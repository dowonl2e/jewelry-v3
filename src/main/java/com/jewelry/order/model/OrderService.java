package com.jewelry.order.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.customer.dto.CustomerResponseDto;
import com.jewelry.customer.entity.CustomerRepositoryImpl;
import com.jewelry.file.dto.FileDto;
import com.jewelry.file.dto.FileResponseDto;
import com.jewelry.file.entity.FileRepository;
import com.jewelry.file.entity.FileRepositoryImpl;
import com.jewelry.file.model.AmazonS3Service;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.dto.OrderResponseDto;
import com.jewelry.order.entity.OrderRepository;
import com.jewelry.order.entity.OrderRepositoryImpl;
import com.jewelry.vender.dto.VenderResponseDto;
import com.jewelry.vender.entity.VenderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderRepositoryImpl orderRepositoryImpl;

  private final CustomerRepositoryImpl customerRepositoryImpl;
  private final VenderRepositoryImpl venderRepositoryImpl;


  private final FileRepository fileRepository;
  private final FileRepositoryImpl fileRepositoryImpl;
  private final AmazonS3Service amazonS3Service;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<OrderResponseDto> orders = orderRepositoryImpl.getSearchOrders(searchDto, pageable);

    searchDto.setTotalPage(orders.getTotalPages());
    searchDto.setHasPrev(orders.hasPrevious());
    searchDto.setHasNext(orders.hasNext());
    searchDto.setTotalCount(orders.getTotalElements());

    response.put("list", orders.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final OrderDto orderDto) throws Exception {
    if (ObjectUtils.isEmpty(orderDto.getCatalogNoArr())) {
      return "fail";
    }

    int result = 0;

    FileDto fileDto = amazonS3Service.uploadFile(orderDto.getOrderFile(), "order", "ORDER");

    Long[] catalogNoArr = orderDto.getCatalogNoArr();
    String[] serialIdArr = orderDto.getSerialIdArr();
    String[] modelIdArr = orderDto.getModelIdArr();
    Long[] venderNoArr = orderDto.getVenderNoArr();
    String[] venderNmArr = orderDto.getVenderNmArr();
    String[] mateialCdArr = orderDto.getMaterialCdArr();
    String[] colorCdArr = orderDto.getColorCdArr();
    String[] mainStoneTypeArr = orderDto.getMainStoneTypeArr();
    String[] subStoneTypeArr = orderDto.getSubStoneTypeArr();
    String[] sizeArr = orderDto.getSizeArr();
    String[] orderDescArr = orderDto.getOrderDescArr();

    Integer[] quantityArr = orderDto.getQuantityArr();

    for(int i = 0 ; i < catalogNoArr.length ; i++) {
      Long catalogNo = catalogNoArr[i];
      if(catalogNo != null && catalogNo > 0) {

        orderDto.setSerialId(serialIdArr[i]);
        orderDto.setCatalogNo(catalogNo);
        orderDto.setModelId(modelIdArr[i]);
        orderDto.setVenderNo(venderNoArr[i]);
        orderDto.setVenderNm(venderNmArr[i]);
        orderDto.setMaterialCd(mateialCdArr[i]);
        orderDto.setColorCd(colorCdArr[i]);
        orderDto.setMainStoneType(mainStoneTypeArr[i]);
        orderDto.setSubStoneType(subStoneTypeArr[i]);
        orderDto.setSize(sizeArr[i]);
        orderDto.setOrderDesc(orderDescArr[i]);

        int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
        for(int j = 0 ; j < quantity ; j++){
          orderDto.setQuantity(1);
          Long orderNo = orderRepository.save(orderDto.toEntity()).getOrderNo();
          orderNo = ObjectUtils.isEmpty(orderNo) ? 0 : orderNo;
          if(orderNo > 0) {
            if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
              fileDto.setRefNo(orderNo);
              fileDto.setInptId(orderDto.getInptId());
              fileRepository.save(fileDto.toEntity());
            }
            result++;
          }
        }
      }
    }

    return result > 0 ? "success" : "res";
  }

  @MenuAuthAnt
  public OrderResponseDto findByOrderNo(final OrderDto orderDto){
    return orderRepositoryImpl.getOrder(orderDto.getOrderNo());
  }

  @MenuAuthAnt
  public String update(final OrderDto orderDto) throws Exception {
    Long[] catalogNoArr = orderDto.getCatalogNoArr();
    Integer[] quantityArr = orderDto.getQuantityArr();
    if(!(catalogNoArr != null && catalogNoArr.length > 0 && quantityArr != null && quantityArr.length > 0)) {
      return "fail";
    }

    FileDto fileDto = amazonS3Service.uploadFile(orderDto.getOrderFile(), "order", "ORDER");

    String[] serialIdArr = orderDto.getSerialIdArr();
    String[] modelIdArr = orderDto.getModelIdArr();
    Long[] venderNoArr = orderDto.getVenderNoArr();
    String[] venderNmArr = orderDto.getVenderNmArr();
    String[] mateialCdArr = orderDto.getMaterialCdArr();
    String[] colorCdArr = orderDto.getColorCdArr();
    String[] mainStoneTypeArr = orderDto.getMainStoneTypeArr();
    String[] subStoneTypeArr = orderDto.getSubStoneTypeArr();
    String[] sizeArr = orderDto.getSizeArr();
    String[] orderDescArr = orderDto.getOrderDescArr();

    //첫 번째 주문이력은 업데이트
    orderDto.setCatalogNo(catalogNoArr[0]);
    orderDto.setSerialId(serialIdArr[0]);
    orderDto.setModelId(modelIdArr[0]);
    orderDto.setVenderNo(venderNoArr[0]);
    orderDto.setVenderNm(venderNmArr[0]);
    orderDto.setMaterialCd(mateialCdArr[0]);
    orderDto.setColorCd(colorCdArr[0]);
    orderDto.setQuantity(1);
    orderDto.setMainStoneType(mainStoneTypeArr[0]);
    orderDto.setSubStoneType(subStoneTypeArr[0]);
    orderDto.setSize(sizeArr[0]);
    orderDto.setOrderDesc(orderDescArr[0]);

    long updateCnt = orderRepositoryImpl.updateOrder(orderDto);

    if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
      fileDto.setRefNo(orderDto.getOrderNo());
      fileDto.setUpdtId(orderDto.getUpdtId());
      fileRepositoryImpl.updateToDelete(fileDto);

      fileDto.setInptId(orderDto.getInptId());
      fileRepository.save(fileDto.toEntity());
    }

    boolean multiInsertCheck = false;
    for(int idx = 1 ; idx < catalogNoArr.length ; idx++) {
      if(catalogNoArr[idx] != null && catalogNoArr[idx] > 0) {
        multiInsertCheck = true;
        break;
      }
    }
    System.out.println("멀티1 : " + multiInsertCheck);
    if(multiInsertCheck == true || quantityArr[0] > 1) {
      if(ObjectUtils.isEmpty(fileDto.getOriginNm())) {
        FileResponseDto fileResponseDto = fileRepositoryImpl.getFileByRef(
            new FileDto(orderDto.getOrderNo(), "ORDER", 1)
        );
        if(fileResponseDto != null) {
          fileDto.setRefInfo(fileResponseDto.getRefInfo());
          fileDto.setFilePath(fileResponseDto.getFilePath());
          fileDto.setOriginNm(fileResponseDto.getOriginNm());
          fileDto.setFileNm(fileResponseDto.getFileNm());
          fileDto.setFileExt(fileResponseDto.getFileExt());
          fileDto.setFileOrd(fileResponseDto.getFileOrd());
          fileDto.setFileSize(fileResponseDto.getFileSize());
        }
      }

      //나머지 주문 추가
      for (int i = 0; i < catalogNoArr.length; i++) {
        Long catalogNo = catalogNoArr[i];
        if (catalogNo != null && catalogNo > 0) {
          orderDto.setOrderNo(null);
          orderDto.setSerialId(serialIdArr[i]);
          orderDto.setCatalogNo(catalogNo);
          orderDto.setModelId(modelIdArr[i]);
          orderDto.setVenderNo(venderNoArr[i]);
          orderDto.setVenderNm(venderNmArr[i]);
          orderDto.setMaterialCd(mateialCdArr[i]);
          orderDto.setColorCd(colorCdArr[i]);
          orderDto.setMainStoneType(mainStoneTypeArr[i]);
          orderDto.setSubStoneType(subStoneTypeArr[i]);
          orderDto.setSize(sizeArr[i]);
          orderDto.setOrderDesc(orderDescArr[i]);

          fileDto.setFileNo(null);

          int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
          if (i == 0) quantity = quantity <= 1 ? 0 : (quantity - 1);

          for (int j = 0; j < quantity; j++) {
            orderDto.setQuantity(1);
            Long orderNo = orderRepository.save(orderDto.toEntity()).getOrderNo();
            orderNo = ObjectUtils.isEmpty(orderNo) ? 0 : orderNo;
            if (orderNo > 0) {
              if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
                fileDto.setRefNo(orderNo);
                fileDto.setInptId(orderDto.getInptId());
                fileRepository.save(fileDto.toEntity());
              }
            }
          }
        }
      }
    }

    return "success";
  }

  @MenuAuthAnt
  public String updateOrdersStep(final OrderDto orderDto) {
    return orderRepositoryImpl.updateOrdersStep(orderDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateOrdersToDelete(final OrderDto orderDto) {
    return orderRepositoryImpl.updateOrdersToDelete(orderDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateOrdersCustomer(final OrderDto orderDto) {
    if(orderDto.getOrderNoArr() == null || orderDto.getOrderNoArr().length == 0){
      return "fail";
    }

    CustomerResponseDto customerResponseDto = customerRepositoryImpl.getCustomer(orderDto.getCustomerNo());
    if(customerResponseDto != null){
      orderDto.setCustomerNm(customerResponseDto.getContractorNm());
      orderDto.setCustomerCel(customerResponseDto.getContractorCel());
    }

    return orderRepositoryImpl.updateOrdersCustomer(orderDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateOrdersVender(final OrderDto orderDto) {
    if(orderDto.getOrderNoArr() == null || orderDto.getOrderNoArr().length == 0){
      return "fail";
    }
    VenderResponseDto venderResponseDto = venderRepositoryImpl.getVenderByVenderNo(orderDto.getVenderNo());
    if(venderResponseDto != null){
      orderDto.setVenderNm(venderResponseDto.getVenderNm());
    }
    return orderRepositoryImpl.updateOrdersVender(orderDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public Map<String, Object> findAllByOrderNos(final OrderDto orderDto){
    Map<String, Object> response = new HashMap<>();

    response.put("list", orderRepositoryImpl.getOrdersByNos(orderDto));

    return response;
  }

  @MenuAuthAnt
  public Map<String, Object> findAllCustomerOrder(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<OrderResponseDto> orders = orderRepositoryImpl.getSearchCustomerOrders(searchDto, pageable);

    searchDto.setTotalPage(orders.getTotalPages());
    searchDto.setHasPrev(orders.hasPrevious());
    searchDto.setHasNext(orders.hasNext());
    searchDto.setTotalCount(orders.getTotalElements());

    response.put("list", orders.getContent());
    response.put("params", searchDto);

    return response;
  }
}
