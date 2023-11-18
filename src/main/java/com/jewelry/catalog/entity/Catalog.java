package com.jewelry.catalog.entity;

import com.jewelry.file.entity.File;
import com.jewelry.stock.entity.Stock;
import com.jewelry.vender.entity.Vender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_catalog")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Catalog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long catalogNo;
  private Long venderNo;
  private String modelId;
  private String modelNm;
  private String stddMaterialCd;
  private String stddWeight;
  private String stddColorCd;
  private String stddSize;
  private String odrNotice;
  private String regDt;
  private Integer basicIdst;
  private Integer mainPrice;
  private Integer subPrice;
  private Integer totalPrice;
  private String delYn = "N";

  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "venderNo", referencedColumnName = "venderNo"
      , insertable = false, updatable = false
  )
  private Vender vender;

  @OneToMany(mappedBy = "catalog")
  private List<Stock> stocks = new ArrayList<>();

//  @OneToOne(mappedBy =  "catalog")
//  private File file;

//  @OneToMany(mappedBy = "catalog") //default : LAZY
//  private List<CatalogStone> catalogStones;

//  @Builder
//  public Catalog(
//      Long catalogNo, Long venderNo, String modelId, String modelNm, String stddMaterialCd
//      , String stddWeight, String stddColorCd, String stddSize, Integer basicIdst) {
//    this.catalogNo = catalogNo;
//    this.venderNo = venderNo;
//    this.modelId = modelId;
//    this.modelNm = modelNm;
//    this.stddMaterialCd = stddMaterialCd;
//    this.stddWeight = stddWeight;
//    this.stddColorCd = stddColorCd;
//    this.stddSize = stddSize;
//    this.basicIdst = basicIdst;
//  }

  @Builder
  public Catalog(
      Long catalogNo, Long venderNo, String modelId, String modelNm, String stddMaterialCd
      , String stddWeight, String stddColorCd, String stddSize, String odrNotice, String regDt
      , Integer basicIdst, Integer mainPrice, Integer subPrice, Integer totalPrice
      , String inptId) {
    this.catalogNo = catalogNo;
    this.venderNo = venderNo;
    this.modelId = modelId;
    this.modelNm = modelNm;
    this.stddMaterialCd = stddMaterialCd;
    this.stddWeight = stddWeight;
    this.stddColorCd = stddColorCd;
    this.stddSize = stddSize;
    this.odrNotice = odrNotice;
    this.regDt = regDt;
    this.basicIdst = basicIdst;
    this.mainPrice = mainPrice;
    this.subPrice = subPrice;
    this.totalPrice = totalPrice;
    this.inptId = inptId;
  }

}
