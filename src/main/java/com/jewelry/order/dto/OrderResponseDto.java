package com.jewelry.order.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderResponseDto extends CommonVO {
  private Long orderNo;
  private String orderType;
  private String storeCd;
  private LocalDateTime receiptDt;
  private LocalDateTime expectedOrdDt;
  private Long customerNo;
  private String customerNm;
  private String customerCel;
  private String orderStep;
  private String serialId;
  private Long catalogNo;
  private String modelId;
  private Long venderNo;
  private String venderNm;
  private String materialCd;
  private String colorCd;
  private Integer quantity;
  private String mainStoneType;
  private String subStoneType;
  private String size;
  private Long stockNo;
  private String orderDesc;

  private String filePath;
  private String fileNm;

  private Long orderCnt;
  private Integer reserveCnt;

  @QueryProjection
  public OrderResponseDto(
      Long orderNo, Long catalogNo, String modelId
      , Long venderNo, String venderNm, String materialCd
      , String colorCd, String mainStoneType, String subStoneType
      , String size, Integer quantity, String orderDesc
      , Long customerNo, String customerNm, String customerCel
  ){
    this.orderNo = orderNo;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.size = size;
    this.quantity = quantity;
    this.orderDesc = orderDesc;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.customerCel = customerCel;
  }

  @QueryProjection
  public OrderResponseDto(
      Long orderNo, String orderType, LocalDateTime receiptDt
      , LocalDateTime expectedOrdDt, String storeCd, Long customerNo
      , String customerNm, String customerCel, String orderStep
      , String serialId, Long catalogNo, String modelId
      , Long venderNo, String venderNm, String materialCd
      , String size, String colorCd, Integer quantity
      , String mainStoneType, String subStoneType, String orderDesc
      , String filePath, String fileNm
  ){
    this.orderNo = orderNo;
    this.orderType = orderType;
    this.storeCd = storeCd;
    this.receiptDt = receiptDt;
    this.expectedOrdDt = expectedOrdDt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.customerCel = customerCel;
    this.orderStep = orderStep;
    this.serialId = serialId;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.materialCd = materialCd;
    this.size = size;
    this.colorCd = colorCd;
    this.quantity = quantity;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.orderDesc = orderDesc;
    this.filePath = filePath;
    this.fileNm = fileNm;
  }

  @QueryProjection
  public OrderResponseDto(
      Long orderNo, String orderType, LocalDateTime receiptDt
      , LocalDateTime expectedOrdDt, String storeCd, Long customerNo
      , String customerNm, String orderStep, Long catalogNo
      , String modelId, Long venderNo, String venderNm
      , String materialCd, String size, String colorCd
      , Integer quantity, String mainStoneType, String subStoneType
      , String orderDesc
      , String filePath, String fileNm
  ){
    this.orderNo = orderNo;
    this.orderType = orderType;
    this.storeCd = storeCd;
    this.receiptDt = receiptDt;
    this.expectedOrdDt = expectedOrdDt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.orderStep = orderStep;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.materialCd = materialCd;
    this.size = size;
    this.colorCd = colorCd;
    this.quantity = quantity;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.orderDesc = orderDesc;
    this.filePath = filePath;
    this.fileNm = fileNm;
  }

  @QueryProjection
  public OrderResponseDto(
      Long orderNo, String orderType, String storeCd
      , LocalDateTime receiptDt, LocalDateTime expectedOrdDt, Long customerNo
      , String customerNm, String customerCel, String orderStep
      , Long catalogNo, String modelId, Long venderNo
      , String venderNm, String serialId, String materialCd
      , String colorCd, Integer quantity, String mainStoneType
      , String subStoneType, String size, String orderDesc
  ){
    this.orderNo = orderNo;
    this.orderType = orderType;
    this.storeCd = storeCd;
    this.receiptDt = receiptDt;
    this.expectedOrdDt = expectedOrdDt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.customerCel = customerCel;
    this.orderStep = orderStep;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.serialId = serialId;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.quantity = quantity;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.size = size;
    this.orderDesc = orderDesc;
  }

  @QueryProjection
  public OrderResponseDto(
      String materialCd, Long orderCnt
  ){
    this.materialCd = materialCd;
    this.orderCnt = orderCnt;
  }
}
