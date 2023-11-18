package com.jewelry.catalog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogStoneResponseDto {

  private Long stoneNo;
  private Long catalogNo;

  private String stoneTypeCd;
  private String stoneNm;
  private Integer beadCnt;
  private String purchasePrice;
  private String stoneDesc;

  @QueryProjection
  public CatalogStoneResponseDto(
    Long stoneNo, Long catalogNo, String stoneTypeCd
    , String stoneNm, Integer beadCnt, String purchasePrice
    , String stoneDesc
  ){
    this.stoneNo = stoneNo;
    this.catalogNo = catalogNo;
    this.stoneTypeCd = stoneTypeCd;
    this.stoneNm = stoneNm;
    this.beadCnt = beadCnt;
    this.purchasePrice = purchasePrice;
    this.stoneDesc = stoneDesc;
  }
}
