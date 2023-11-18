package com.jewelry.order.entity;

import com.jewelry.customer.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_order")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String delYn = "N";
  private String inptId;

  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "customerNo", referencedColumnName = "customerNo"
      , insertable = false, updatable = false
  )
  private Customer customer;

  @Builder
  public Order(
      Long orderNo, String orderType, String storeCd
      , LocalDateTime receiptDt, LocalDateTime expectedOrdDt, Long customerNo
      , String customerNm, String customerCel, String orderStep
      , String serialId, Long catalogNo, String modelId
      , Long venderNo, String venderNm, String materialCd
      , String colorCd, Integer quantity, String mainStoneType
      , String subStoneType, String size, Long stockNo
      , String orderDesc, String inptId
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
    this.colorCd = colorCd;
    this.quantity = quantity;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.size = size;
    this.stockNo = stockNo;
    this.orderDesc = orderDesc;
    this.inptId = inptId;
  }

//  @Builder
//  public Order(
//      Long orderNo, String storeCd, LocalDateTime receiptDt
//      , LocalDateTime expectedOrdDt, Long customerNo, String customerNm
//      , String customerCel, String serialId, Long catalogNo
//      , String modelId, Long venderNo, String venderNm
//      , String materialCd, String colorCd, Integer quantity
//      , String mainStoneType, String subStoneType, String size
//      , String orderDesc, String updtId, LocalDateTime updtDt
//  ){
//    this.orderNo = orderNo;
//    this.storeCd = storeCd;
//    this.receiptDt = receiptDt;
//    this.expectedOrdDt = expectedOrdDt;
//    this.customerNo = customerNo;
//    this.customerNm = customerNm;
//    this.customerCel = customerCel;
//    this.serialId = serialId;
//    this.catalogNo = catalogNo;
//    this.modelId = modelId;
//    this.venderNo = venderNo;
//    this.venderNm = venderNm;
//    this.materialCd = materialCd;
//    this.colorCd = colorCd;
//    this.quantity = quantity;
//    this.mainStoneType = mainStoneType;
//    this.subStoneType = subStoneType;
//    this.size = size;
//    this.orderDesc = orderDesc;
//    this.updtId = updtId;
//    this.updtDt = updtDt;
//  }
}
