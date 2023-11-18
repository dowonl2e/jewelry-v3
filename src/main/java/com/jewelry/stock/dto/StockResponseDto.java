package com.jewelry.stock.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StockResponseDto extends CommonVO {
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
  private String delYn;
  private Long stockCnt;
  private LocalDateTime updtDt;

  private String filePath;
  private String fileNm;

  @QueryProjection
  public StockResponseDto(
      Long stockNo, String storeCd, LocalDateTime regDt
      , String stockTypeCd, String size, String stockDesc
      , Long catalogNo, String modelId, String materialCd
      , String colorCd, String mainStoneType, String subStoneType
      , Integer quantity, Double perWeightGram, Integer realPchGoldPrice
      , Integer perPriceBasic, Integer perPriceAdd, Integer perPriceMain
      , Integer perPriceSub, Integer multipleCnt, Long customerNo
      , String customerNm
  ){
    this.stockNo = stockNo;
    this.storeCd = storeCd;
    this.regDt = regDt;
    this.stockTypeCd = stockTypeCd;
    this.size = size;
    this.stockDesc = stockDesc;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.quantity = quantity;
    this.perWeightGram = perWeightGram;
    this.realPchGoldPrice = realPchGoldPrice;
    this.perPriceBasic = perPriceBasic;
    this.perPriceAdd = perPriceAdd;
    this.perPriceMain = perPriceMain;
    this.perPriceSub = perPriceSub;
    this.multipleCnt = multipleCnt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
  }

  //리스트 및 세부내용 사용 컬럼
  @QueryProjection
  public StockResponseDto(
      Long stockNo, String storeCd, LocalDateTime regDt
      , String stockTypeCd, Long catalogNo, String modelId
      , Long venderNo, String venderNm, String size
      , String stockDesc, String materialCd
      , String colorCd, String mainStoneType, String subStoneType
      , Integer quantity, Double perWeightGram, Integer realPchGoldPrice
      , Integer perPriceMain, Integer perPriceSub, Integer perPriceBasic
      , Integer perPriceAdd, Integer multipleCnt
  ){
    this.stockNo = stockNo;
    this.storeCd = storeCd;
    this.regDt = regDt;
    this.stockTypeCd = stockTypeCd;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.size = size;
    this.stockDesc = stockDesc;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.quantity = quantity;
    this.perWeightGram = perWeightGram;
    this.realPchGoldPrice = realPchGoldPrice;
    this.perPriceMain = perPriceMain;
    this.perPriceSub = perPriceSub;
    this.perPriceBasic = perPriceBasic;
    this.perPriceAdd = perPriceAdd;
    this.multipleCnt = multipleCnt;
  }

  @QueryProjection
  public StockResponseDto(
      Long stockNo, String storeCd, LocalDateTime regDt
      , String stockTypeCd, Long catalogNo, String modelId
      , Long venderNo, String venderNm, String size
      , String stockDesc, String materialCd
      , String colorCd, String mainStoneType, String subStoneType
      , Integer quantity, Double perWeightGram, Integer realPchGoldPrice
      , Integer perPriceMain, Integer perPriceSub, Integer perPriceBasic
      , Integer perPriceAdd, Integer multipleCnt
      , String filePath, String fileNm
  ){
    this.stockNo = stockNo;
    this.storeCd = storeCd;
    this.regDt = regDt;
    this.stockTypeCd = stockTypeCd;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.size = size;
    this.stockDesc = stockDesc;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.quantity = quantity;
    this.perWeightGram = perWeightGram;
    this.realPchGoldPrice = realPchGoldPrice;
    this.perPriceMain = perPriceMain;
    this.perPriceSub = perPriceSub;
    this.perPriceBasic = perPriceBasic;
    this.perPriceAdd = perPriceAdd;
    this.multipleCnt = multipleCnt;
    this.filePath = filePath;
    this.fileNm = fileNm;
  }

  //고객조회용 컬럼
  @QueryProjection
  public StockResponseDto(
      Long stockNo, String stockTypeCd, Long customerNo, String customerNm
  ){
    this.stockNo = stockNo;
    this.stockTypeCd = stockTypeCd;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
  }

  //누적 재고 조회
  @QueryProjection
  public StockResponseDto(
    Long stockNo, String storeCd, LocalDateTime regDt
    , String stockTypeCd, String size, String stockDesc
    , Long catalogNo, String modelId, String materialCd
    , String colorCd, String mainStoneType, String subStoneType
    , Integer quantity, Double perWeightGram, Integer realPchGoldPrice
    , Integer perPriceBasic, Integer perPriceAdd, Integer perPriceMain
    , Integer perPriceSub, Integer multipleCnt, String delYn
    , String saleYn, LocalDateTime updtDt
  ){
    this.stockNo = stockNo;
    this.storeCd = storeCd;
    this.regDt = regDt;
    this.stockTypeCd = stockTypeCd;
    this.size = size;
    this.stockDesc = stockDesc;
    this.catalogNo = catalogNo;
    this.modelId = modelId;
    this.materialCd = materialCd;
    this.colorCd = colorCd;
    this.mainStoneType = mainStoneType;
    this.subStoneType = subStoneType;
    this.quantity = quantity;
    this.perWeightGram = perWeightGram;
    this.realPchGoldPrice = realPchGoldPrice;
    this.perPriceBasic = perPriceBasic;
    this.perPriceAdd = perPriceAdd;
    this.perPriceMain = perPriceMain;
    this.perPriceSub = perPriceSub;
    this.multipleCnt = multipleCnt;
    this.delYn = delYn;
    this.saleYn = saleYn;
    this.updtDt = updtDt;
  }

  @QueryProjection
  public StockResponseDto(
      Long stockNo, String storeCd, Long catalogNo
      , String modelId, Long venderNo, String venderNm
      , String materialCd, String colorCd, String mainStoneType
      , String subStoneType, String size, String stockDesc
  ){
    this.stockNo = stockNo;
    this.storeCd = storeCd;
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
  }

  @QueryProjection
  public StockResponseDto(
      String materialCd, Double perWeightGram, Long stockCnt
  ){
    this.materialCd = materialCd;
    this.perWeightGram = perWeightGram;
    this.stockCnt = stockCnt;
  }

}
