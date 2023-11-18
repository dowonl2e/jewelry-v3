package com.jewelry.catalog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_catalog_stone")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogStone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long stoneNo;
  private Long catalogNo;
  private String stoneTypeCd;
  private String stoneNm;
  private Integer beadCnt;
  private String purchasePrice;
  private String stoneDesc;
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @Transient
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "catalogNo"
      , referencedColumnName = "catalogNo"
      , insertable = false
      , updatable = false
  )
  private Catalog catalog;

  @Builder
  public CatalogStone (
      Long stoneNo, Long catalogNo
      , String stoneTypeCd, String stoneNm
      , Integer beadCnt, String purchasePrice
      , String stoneDesc, String inptId){
    this.stoneNo = stoneNo;
    this.catalogNo = catalogNo;
    this.stoneTypeCd = stoneTypeCd;
    this.stoneNm = stoneNm;
    this.beadCnt = beadCnt;
    this.purchasePrice = purchasePrice;
    this.stoneDesc = stoneDesc;
    this.inptId = inptId;
  }
}
