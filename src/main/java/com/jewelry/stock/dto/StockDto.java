package com.jewelry.stock.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.stock.entity.Stock;
import com.jewelry.util.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class StockDto extends CommonDto {

  private Long stockNo;
  private String regDt;
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
  private String saleDt;
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
  private String customerCel;
  private String orderYn;

  private MultipartFile stockFile;
  private Long[] stockNoArr;
  private Long[] catalogNoArr;
  private String[] modelIdArr;
  private Long[] venderNoArr;
  private String[] venderNmArr;
  private String[] materialCdArr;
  private String[] colorCdArr;
  private String[] mainStoneTypeArr;
  private String[] subStoneTypeArr;
  private String[] sizeArr;
  private String[] stockDescArr;
  private Integer[] quantityArr;
  private Double[] perWeightGramArr;
  private Integer[] perPriceBasicArr;
  private Integer[] perPriceAddArr;
  private Integer[] perPriceMainArr;
  private Integer[] perPriceSubArr;
  private Integer[] perPriceGoldRealArr;
  private Integer[] multipleCntArr;

  public Stock toEntity(){
    return Stock.builder()
        .regDt(Utils.convertLocalDateTime(this.regDt))
        .stockTypeCd(this.stockTypeCd)
        .storeCd(this.storeCd)
        .realPchGoldPrice(this.realPchGoldPrice)
        .catalogNo(this.catalogNo)
        .modelId(this.modelId)
        .venderNo(this.venderNo)
        .venderNm(this.venderNm)
        .materialCd(this.materialCd)
        .colorCd(this.colorCd)
        .mainStoneType(this.mainStoneType)
        .subStoneType(this.subStoneType)
        .size(this.size)
        .stockDesc(this.stockDesc)
        .quantity(this.quantity)
        .perWeightGram(this.perWeightGram)
        .perPriceBasic(this.perPriceBasic)
        .perPriceAdd(this.perPriceAdd)
        .perPriceMain(this.perPriceMain)
        .perPriceSub(this.perPriceSub)
        .perPriceGoldReal(this.perPriceGoldReal)
        .multipleCnt(this.multipleCnt)
        .saleYn(this.saleYn)
        .saleDt(Utils.convertLocalDateTime(this.saleDt))
        .salePrice(this.salePrice)
        .recPayTypeCd(this.recPayTypeCd)
        .cardPrice(this.cardPrice)
        .cashPrice(this.cashPrice)
        .maintPrice(this.maintPrice)
        .pntPrice(this.pntPrice)
        .etcPrice(this.etcPrice)
        .accuPnt(this.accuPnt)
        .customerNo(this.customerNo)
        .customerNm(this.customerNm)
        .orderYn(this.orderYn)
        .inptId(super.getInptId())
        .build();
  }
}
