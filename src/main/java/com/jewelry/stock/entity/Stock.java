package com.jewelry.stock.entity;

import com.jewelry.catalog.entity.Catalog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_stock")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long stockNo;

  private LocalDateTime regDt;
  private String stockTypeCd;
  private String storeCd;
  private Integer realPchGoldPrice;
  private Long catalogNo;
  private String modelId;
  private Long venderNo;
  private String venderNm;
  private String materialCd;
  private String colorCd;
  private String mainStoneType;
  private String subStoneType;
  private String size;
  private String stockDesc;
  private Integer quantity;
  private Double perWeightGram;
  private Integer perPriceBasic;
  private Integer perPriceAdd;
  private Integer perPriceMain;
  private Integer perPriceSub;
  private Integer perPriceGoldReal;
  private Integer multipleCnt;
  private String saleYn;
  private LocalDateTime saleDt;
  private Integer salePrice;
  private String recPayTypeCd;
  private Integer cardPrice;
  private Integer cashPrice;
  private Integer maintPrice;
  private Integer pntPrice;
  private Integer etcPrice;
  private Integer accuPnt;
  private Long customerNo;
  private String customerNm;
  private String orderYn;
  private String delYn = "N";
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "catalogNo", referencedColumnName = "catalogNo"
      , insertable = false, updatable = false
  )
  private Catalog catalog;

  @Builder
  public Stock(
      Long stockNo, LocalDateTime regDt, String stockTypeCd
      , String storeCd, Integer realPchGoldPrice, Long catalogNo
      , String modelId, Long venderNo, String venderNm
      , String materialCd, String colorCd, String mainStoneType
      , String subStoneType, String size, String stockDesc
      , Integer quantity, Double perWeightGram, Integer perPriceBasic
      , Integer perPriceAdd, Integer perPriceMain, Integer perPriceSub
      , Integer perPriceGoldReal, Integer multipleCnt, String saleYn
      , LocalDateTime saleDt, Integer salePrice, String recPayTypeCd
      , Integer cardPrice, Integer cashPrice, Integer maintPrice
      , Integer pntPrice, Integer etcPrice, Integer accuPnt
      , Long customerNo, String customerNm, String orderYn
      , String inptId
  ){
    this.stockNo = stockNo;
    this.regDt = regDt;
    this.stockTypeCd = stockTypeCd;
    this.storeCd = storeCd;
    this.realPchGoldPrice = realPchGoldPrice;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.size = size;
    this.stockDesc = stockDesc;
    this.quantity = quantity;
    this.perWeightGram = perWeightGram;
    this.perPriceBasic = perPriceBasic;
    this.perPriceAdd = perPriceAdd;
    this.perPriceMain = perPriceMain;
    this.perPriceSub = perPriceSub;
    this.perPriceGoldReal = perPriceGoldReal;
    this.multipleCnt = multipleCnt;
    this.saleYn = saleYn;
    this.saleDt = saleDt;
    this.salePrice = salePrice;
    this.recPayTypeCd = recPayTypeCd;
    this.cardPrice = cardPrice;
    this.cashPrice = cashPrice;
    this.maintPrice = maintPrice;
    this.pntPrice = pntPrice;
    this.etcPrice = etcPrice;
    this.accuPnt = accuPnt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.orderYn = orderYn;
    this.inptId = inptId;
  }


}
