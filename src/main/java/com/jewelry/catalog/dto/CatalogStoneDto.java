package com.jewelry.catalog.dto;

import com.jewelry.catalog.entity.CatalogStone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogStoneDto {
  private Long stoneNo;
  private Long catalogNo;

  private String stoneTypeCd;
  private String stoneNm;
  private Integer beadCnt;
  private String purchasePrice;
  private String stoneDesc;
  private String inptId;
  private String updtId;

  private String[] stoneTypeCdArr;
  private String[] stoneNmArr;
  private Integer[] beadCntArr;
  private String[] purchasePriceArr;
  private String[] stoneDescArr;

  public void setValueByArray(String stoneTypeCd, String stoneNm, Integer beadCnt, String purchasePrice, String stoneDesc){
    this.stoneTypeCd = stoneTypeCd;
    this.stoneNm = stoneNm;
    this.beadCnt = beadCnt;
    this.purchasePrice = purchasePrice;
    this.stoneDesc = stoneDesc;
  }

  public CatalogStone toEntity(){
    return CatalogStone.builder()
        .stoneNo(this.stoneNo)
        .catalogNo(this.catalogNo)
        .stoneTypeCd(this.stoneTypeCd)
        .stoneNm(this.stoneNm)
        .beadCnt(this.beadCnt == null ? 0 : this.beadCnt)
        .purchasePrice(this.purchasePrice)
        .stoneDesc(this.stoneDesc)
        .inptId(this.inptId)
        .build();
  }
}
