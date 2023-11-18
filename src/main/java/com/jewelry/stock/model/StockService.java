package com.jewelry.stock.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.file.dto.FileDto;
import com.jewelry.file.dto.FileResponseDto;
import com.jewelry.file.entity.FileRepository;
import com.jewelry.file.entity.FileRepositoryImpl;
import com.jewelry.file.model.AmazonS3Service;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.entity.OrderRepository;
import com.jewelry.stock.dto.StockDto;
import com.jewelry.stock.dto.StockResponseDto;
import com.jewelry.stock.entity.StockRepository;
import com.jewelry.stock.entity.StockRepositoryImpl;
import com.jewelry.util.Utils;
import com.jewelry.vender.dto.VenderResponseDto;
import com.jewelry.vender.entity.VenderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

  private final StockRepositoryImpl stockRepositoryImpl;
  private final StockRepository stockRepository;
  private final VenderRepositoryImpl venderRepositoryImpl;
  private final OrderRepository orderRepository;

  private final FileRepository fileRepository;
  private final FileRepositoryImpl fileRepositoryImpl;
  private final AmazonS3Service amazonS3Service;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<StockResponseDto> stocks = stockRepositoryImpl.getSearchStocks(searchDto, pageable);

    searchDto.setTotalPage(stocks.getTotalPages());
    searchDto.setHasPrev(stocks.hasPrevious());
    searchDto.setHasNext(stocks.hasNext());
    searchDto.setTotalCount(stocks.getTotalElements());

    response.put("list", stocks.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public List<StockResponseDto> findAllPrevStocks(){
    return stockRepositoryImpl.getPrevStocks();
  }
  @MenuAuthAnt
  public String save(final StockDto stockDto) throws Exception {
    if(stockDto.getCatalogNoArr() == null || stockDto.getCatalogNoArr().length == 0){
      return "fail";
    }

    int saveCnt = 0;
    FileDto fileDto = amazonS3Service.uploadFile(stockDto.getStockFile(), "stock", "STOCK");

    Long[] catalogNoArr = stockDto.getCatalogNoArr();
    String[] modelIdArr = stockDto.getModelIdArr();
    Long[] venderNoArr = stockDto.getVenderNoArr();
    String[] venderNmArr = stockDto.getVenderNmArr();
    String[] mateialCdArr = stockDto.getMaterialCdArr();
    String[] colorCdArr = stockDto.getColorCdArr();
    String[] mainStoneTypeArr = stockDto.getMainStoneTypeArr();
    String[] subStoneTypeArr = stockDto.getSubStoneTypeArr();
    String[] sizeArr = stockDto.getSizeArr();
    String[] stockDescArr = stockDto.getStockDescArr();
    Integer[] quantityArr = stockDto.getQuantityArr();

    Double[] perWeightGramArr = stockDto.getPerWeightGramArr();
    Integer[] perPriceBasicArr = stockDto.getPerPriceBasicArr();
    Integer[] perPriceAddArr = stockDto.getPerPriceAddArr();
    Integer[] perPriceMainArr = stockDto.getPerPriceMainArr();
    Integer[] perPriceSubArr = stockDto.getPerPriceSubArr();
    Integer[] multipleCntArr = stockDto.getMultipleCntArr();

    for(int i = 0 ; i < catalogNoArr.length ; i++) {
      Long catalogNo = catalogNoArr[i];
      if(catalogNo != null && catalogNo > 0){
        stockDto.setCatalogNo(catalogNo);
        stockDto.setModelId(modelIdArr[i]);
        stockDto.setVenderNo(venderNoArr[i]);
        stockDto.setVenderNm(venderNmArr[i]);
        stockDto.setMaterialCd(mateialCdArr[i]);
        stockDto.setColorCd(colorCdArr[i]);
        stockDto.setMainStoneType(mainStoneTypeArr[i]);
        stockDto.setSubStoneType(subStoneTypeArr[i]);
        stockDto.setSize(sizeArr[i]);
        stockDto.setStockDesc(stockDescArr[i]);
        stockDto.setQuantity(1);
        stockDto.setPerWeightGram(perWeightGramArr[i]);
        stockDto.setPerPriceBasic(perPriceBasicArr[i]);
        stockDto.setPerPriceAdd(perPriceAddArr[i]);
        stockDto.setPerPriceMain(perPriceMainArr[i]);
        stockDto.setPerPriceSub(perPriceSubArr[i]);
        stockDto.setMultipleCnt(multipleCntArr[i]);

        int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
        for(int j = 0 ; j < quantity ; j++){
          Long stockNo = stockRepository.save(stockDto.toEntity()).getStockNo();
          if(stockNo != null && stockNo > 0) {
            if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
              fileDto.setInptId(stockDto.getInptId());
              fileDto.setRefNo(stockNo);
              fileRepository.save(fileDto.toEntity());
            }
          }
          saveCnt++;
        }
      }
    }
    return saveCnt > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public StockResponseDto findByStockNo(final StockDto stockDto){
    return stockRepositoryImpl.getStock(stockDto.getStockNo());
  }

  @MenuAuthAnt
  public StockResponseDto findCustomerByStockNo(final StockDto stockDto) {
    return stockRepositoryImpl.getStockCustomer(stockDto.getStockNo());
  }

  @MenuAuthAnt
  public String update(final StockDto stockDto) throws Exception {
    if(stockDto.getCatalogNoArr() == null || stockDto.getCatalogNoArr().length == 0
        || stockDto.getQuantityArr() == null || stockDto.getQuantityArr().length == 0){
      return "fail";
    }

    int saveCnt = 0;
    FileDto fileDto = amazonS3Service.uploadFile(stockDto.getStockFile(), "stock", "STOCK");

    Long[] catalogNoArr = stockDto.getCatalogNoArr();
    String[] modelIdArr = stockDto.getModelIdArr();
    Long[] venderNoArr = stockDto.getVenderNoArr();
    String[] venderNmArr = stockDto.getVenderNmArr();
    String[] mateialCdArr = stockDto.getMaterialCdArr();
    String[] colorCdArr = stockDto.getColorCdArr();
    String[] mainStoneTypeArr = stockDto.getMainStoneTypeArr();
    String[] subStoneTypeArr = stockDto.getSubStoneTypeArr();
    String[] sizeArr = stockDto.getSizeArr();
    String[] stockDescArr = stockDto.getStockDescArr();
    Integer[] quantityArr = stockDto.getQuantityArr();

    Double[] perWeightGramArr = stockDto.getPerWeightGramArr();
    Integer[] perPriceBasicArr = stockDto.getPerPriceBasicArr();
    Integer[] perPriceAddArr = stockDto.getPerPriceAddArr();
    Integer[] perPriceMainArr = stockDto.getPerPriceMainArr();
    Integer[] perPriceSubArr = stockDto.getPerPriceSubArr();
    Integer[] multipleCntArr = stockDto.getMultipleCntArr();

    //첫번째 재고 데이터 업데이트
    stockDto.setCatalogNo(catalogNoArr[0]);
    stockDto.setModelId(modelIdArr[0]);
    stockDto.setVenderNo(venderNoArr[0]);
    stockDto.setVenderNm(venderNmArr[0]);
    stockDto.setMaterialCd(mateialCdArr[0]);
    stockDto.setColorCd(colorCdArr[0]);
    stockDto.setMainStoneType(mainStoneTypeArr[0]);
    stockDto.setSubStoneType(subStoneTypeArr[0]);
    stockDto.setSize(sizeArr[0]);
    stockDto.setStockDesc(stockDescArr[0]);
    stockDto.setQuantity(1);
    stockDto.setPerWeightGram(perWeightGramArr[0]);
    stockDto.setPerPriceBasic(perPriceBasicArr[0]);
    stockDto.setPerPriceAdd(perPriceAddArr[0]);
    stockDto.setPerPriceMain(perPriceMainArr[0]);
    stockDto.setPerPriceSub(perPriceSubArr[0]);
    stockDto.setMultipleCnt(multipleCntArr[0]);
    long updateCnt = stockRepositoryImpl.updateStock(stockDto);

    if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
      fileDto.setUpdtId(stockDto.getUpdtId());
      fileDto.setRefNo(stockDto.getStockNo());
      fileRepositoryImpl.updateToDelete(fileDto);

      fileDto.setInptId(stockDto.getInptId());
      fileRepository.save(fileDto.toEntity());
    }

    boolean multiInsertCheck = false;
    for(int idx = 1 ; idx < catalogNoArr.length ; idx++) {
      if(catalogNoArr[idx] != null && catalogNoArr[idx] > 0) {
        multiInsertCheck = true;
        break;
      }
    }

    if(multiInsertCheck == true || quantityArr[0] > 1) {
      if(ObjectUtils.isEmpty(fileDto.getOriginNm())) {
        FileResponseDto fileResponseDto = fileRepositoryImpl.getFileByRef(new FileDto(stockDto.getStockNo(), "STOCK", 1));
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

      for(int i = 0 ; i < catalogNoArr.length ; i++) {
        Long catalogNo = catalogNoArr[i];
        if(catalogNo != null && catalogNo > 0) {
          stockDto.setStockNo(null);
          stockDto.setCatalogNo(catalogNo);
          stockDto.setModelId(modelIdArr[i]);
          stockDto.setVenderNo(venderNoArr[i]);
          stockDto.setVenderNm(venderNmArr[i]);
          stockDto.setMaterialCd(mateialCdArr[i]);
          stockDto.setColorCd(colorCdArr[i]);
          stockDto.setMainStoneType(mainStoneTypeArr[i]);
          stockDto.setSubStoneType(subStoneTypeArr[i]);
          stockDto.setSize(sizeArr[i]);
          stockDto.setStockDesc(stockDescArr[i]);
          stockDto.setQuantity(1);
          stockDto.setPerWeightGram(perWeightGramArr[i]);
          stockDto.setPerPriceBasic(perPriceBasicArr[i]);
          stockDto.setPerPriceAdd(perPriceAddArr[i]);
          stockDto.setPerPriceMain(perPriceMainArr[i]);
          stockDto.setPerPriceSub(perPriceSubArr[i]);
          stockDto.setMultipleCnt(multipleCntArr[i]);

          int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
          if(i == 0)
            quantity = quantity <= 1 ? 0 : (quantity-1);

          for(int j = 0 ; j < quantity ; j++){
            Long stockNo = stockRepository.save(stockDto.toEntity()).getStockNo();
            if(stockNo != null && stockNo > 0) {
              if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
                fileDto.setInptId(stockDto.getInptId());
                fileDto.setRefNo(stockNo);
                fileRepository.save(fileDto.toEntity());
              }
            }
          }
        }
        updateCnt++;
      }
    }

    return updateCnt > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateToDelete(final StockDto stockDto){
    return stockRepositoryImpl.updateStockToDelete(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksToDelete(final StockDto stockDto){
    return stockRepositoryImpl.updateStocksToDelete(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksToSale(final StockDto stockDto){
    return stockRepositoryImpl.updateStocksToSale(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksRegDt(final StockDto stockDto){
    return stockRepositoryImpl.updateStocksRegDt(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksType(final StockDto stockDto){
    return stockRepositoryImpl.updateStocksType(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksVender(final StockDto stockDto){
    if(stockDto.getStockNoArr() != null && stockDto.getStockNoArr().length > 0) {
      VenderResponseDto venderResponseDto = venderRepositoryImpl.getVenderByVenderNo(stockDto.getVenderNo());
      stockDto.setVenderNm(venderResponseDto != null ? venderResponseDto.getVenderNm() : null);
    }
    return stockRepositoryImpl.updateStocksVender(stockDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public Map<String, Object> findAllAccumulationStock(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<StockResponseDto> stocks = stockRepositoryImpl.getSearchAccumulationStocks(searchDto, pageable);

    searchDto.setTotalPage(stocks.getTotalPages());
    searchDto.setHasPrev(stocks.hasPrevious());
    searchDto.setHasNext(stocks.hasNext());
    searchDto.setTotalCount(stocks.getTotalElements());

    response.put("list", stocks.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String saveCustomerOrder(final StockDto stockDto){
    if(stockDto.getStockNoArr() == null || stockDto.getStockNoArr().length == 0) {
      return "fail";
    }
    long updateCnt = stockRepositoryImpl.updateStocksOrder(stockDto);
    if(updateCnt == 0){
      return "fail";
    }

    List<StockResponseDto> stocks = stockRepositoryImpl.getStockListByNos(stockDto.getStockNoArr());
    if(stocks != null && stocks.size() > 0) {
      OrderDto orderDto = new OrderDto();
      orderDto.setInptId(stockDto.getInptId());
      orderDto.setOrderType("CUSTOMER");
      orderDto.setOrderStep("B");
      for(StockResponseDto stock : stocks) {
        orderDto.setStockNo(stock.getStockNo());
        orderDto.setStoreCd(stock.getStoreCd());
        orderDto.setReceiptDt(Utils.getTodayDateFormat("yyyy-MM-dd"));
        orderDto.setExpectedOrdDt(Utils.getTodayDateFormat("yyyy-MM-dd"));
        orderDto.setCustomerNo(stockDto.getCustomerNo());
        orderDto.setCustomerNm(stockDto.getCustomerNm());
        orderDto.setCustomerCel(stockDto.getCustomerCel());
        orderDto.setCatalogNo(stock.getCatalogNo());
        orderDto.setModelId(stock.getModelId());
        orderDto.setVenderNo(stock.getVenderNo());
        orderDto.setVenderNm(stock.getVenderNm());
        orderDto.setMaterialCd(stock.getMaterialCd());
        orderDto.setColorCd(stock.getColorCd());
        orderDto.setQuantity(1);
        orderDto.setMainStoneType(stock.getMainStoneType());
        orderDto.setSubStoneType(stock.getSubStoneType());
        orderDto.setSize(stock.getSize());
        orderDto.setOrderDesc(stock.getStockDesc());
        Long orderNo = orderRepository.save(orderDto.toEntity()).getOrderNo();
        if(orderNo != null && orderNo > 0){
          FileResponseDto fileResponseDto = fileRepositoryImpl.getFileByRef(new FileDto(stock.getStockNo(), "STOCK", 1));
          if(fileResponseDto != null) {
            FileDto fileDto = new FileDto();
            fileDto.setInptId(stockDto.getInptId());
            fileDto.setRefNo(orderNo);
            fileDto.setRefInfo("ORDER");
            fileDto.setFilePath(fileResponseDto.getFilePath());
            fileDto.setFileNm(fileResponseDto.getFileNm());
            fileDto.setOriginNm(fileResponseDto.getOriginNm());
            fileDto.setFileOrd(1);
            fileDto.setFileExt(fileResponseDto.getFileExt());
            fileDto.setFileSize(fileResponseDto.getFileSize());
            fileDto.setVersionId(fileResponseDto.getVersionId());
            fileRepository.save(fileDto.toEntity());
          }
        }
        updateCnt++;
      }
    }
    return updateCnt > 0 ? "success" : "fail";
  }

  public Map<String, Object> isSameCustomer(String stocksno) {
    Map<String, Object> response = new HashMap<>();
    if(ObjectUtils.isEmpty(stocksno)) {
      response.put("result", "fail1");
      //map.put("msg", "판매할 재고를 선택해주세요.");
    }
    else {
      Long[] stockNoArr = Arrays.stream(stocksno.split(","))
          .map(String::trim)
          .map(Long::valueOf)
          .toArray(Long[]::new);

      List<StockResponseDto> stocks = stockRepositoryImpl.getStockListByStockNos(stockNoArr);
      //재고 내역 없음
      if(CollectionUtils.isEmpty(stocks)) {
        response.put("result", "fail2");
      }
      else {
        int i = 0, orderCnt = 0, stockCnt = 0;

        Long customerNo = stocks.get(0).getCustomerNo() == null ? (long)0 : stocks.get(0).getCustomerNo();
        String customerNm = ObjectUtils.isEmpty(stocks.get(0).getCustomerNm()) ? "" : stocks.get(0).getCustomerNm();

        boolean isDupType = false, isNotSameCustomer = false;
        for(StockResponseDto tempvo : stocks) {
          if(!ObjectUtils.isEmpty(tempvo.getStockTypeCd())) {
            if(ObjectUtils.nullSafeEquals(tempvo.getStockTypeCd(), "OC03"))
              orderCnt++;
            if(ObjectUtils.nullSafeEquals(tempvo.getStockTypeCd(), "OC01"))
              stockCnt++;

            if(orderCnt > 0 && stockCnt > 0) {
              isDupType = true;
              break;
            }
          }
          if(i > 0 && stocks.get(i).getCustomerNo() != null
              && stocks.get(i).getCustomerNo() > 0
              && customerNo != stocks.get(i).getCustomerNo()) {
            isNotSameCustomer = true;
            break;
          }
          i++;
        }
        //주문재고와 일반재고는 별도로 판매 가능
        if(isDupType) {
          response.put("result", "fail3");
        }
        else {
          //주문재고는 동일 고객의 재고만 판매 가능
          if(isNotSameCustomer) {
            response.put("result", "fail4");
          }
          else {
            response.put("result", "success");
            response.put("customerNo", customerNo);
            response.put("customerNm", customerNm);
          }
        }
      }
    }
    return response;
  }

}
