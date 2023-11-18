package com.jewelry.sale.dto;

import com.jewelry.common.domain.CommonVO;
import com.jewelry.common.dto.CommonDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleResponseDto extends CommonVO {

  private Long saleNo;
  private String storeCd;
  private LocalDateTime saleDt;
  private String saleType;
  private String saleType2;
  private String saleDay;
  private Long customerNo;
  private String customerNm;
  private Long catalogNo;
  private String modelId;
  private Integer realPchGoldPrice;
  private String materialCd;
  private Double perWeightGram;
  private String saleDesc;
  private String mainStoneType;
  private String subStoneType;
  private Integer quantity;
  private Integer purchasePrice;
  private Integer consumerPrice;
  private Integer salePrice;
  private String recPayTypeCd;
  private Integer cardPrice;
  private Integer cashPrice;
  private Integer maintPrice;
  private Integer pntPrice;
  private Integer etcPrice;
  private Integer accuPnt;
  private Integer saleMonth;

  @QueryProjection
  public SaleResponseDto(
    Long saleNo, String storeCd, LocalDateTime saleDt
    , String saleType2, String saleDay, String saleType
    , Long customerNo, String customerNm, Long catalogNo
    , String modelId, Integer realPchGoldPrice, String materialCd
    , Double perWeightGram, String saleDesc, String mainStoneType
    , String subStoneType, Integer quantity, Integer purchasePrice
    , Integer consumerPrice, Integer salePrice, String recPayTypeCd
    , Integer cardPrice, Integer cashPrice, Integer maintPrice
    , Integer pntPrice, Integer etcPrice, Integer accuPnt
  ){
    this.saleNo = saleNo;
    this.storeCd = storeCd;
    this.saleDt = saleDt;
    this.saleType2 = saleType2;
    this.saleDay = saleDay;
    this.saleType = saleType;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.realPchGoldPrice = realPchGoldPrice;
    this.materialCd = materialCd;
    this.perWeightGram = perWeightGram;
    this.saleDesc = saleDesc;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
    this.consumerPrice = consumerPrice;
    this.salePrice = salePrice;
    this.recPayTypeCd = recPayTypeCd;
    this.cardPrice = cardPrice;
    this.cashPrice = cashPrice;
    this.maintPrice = maintPrice;
    this.pntPrice = pntPrice;
    this.etcPrice = etcPrice;
    this.accuPnt = accuPnt;
  }

  @QueryProjection
  public SaleResponseDto(
    Integer saleMonth, Integer salePrice
  ){
    this.saleMonth = saleMonth;
    this.salePrice = salePrice;
  }
}
