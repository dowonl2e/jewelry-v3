package com.jewelry.cash.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tb_cash")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cash {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cashNo;
  private LocalDateTime regDt;
  private String storeCd;
  private String cashTypeCd;
  private String cashTypeCd2;
  private String bankbookCd;
  private Long venderNo;
  private String venderNm;
  private String historyDesc;
  private String materialCd;
  private Double weightGram;
  private Integer quantity;
  private Integer unitPrice;
  private String delYn = "N";
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @Builder
  public Cash(
      Long cashNo, LocalDateTime regDt, String storeCd
      , String cashTypeCd, String cashTypeCd2, String bankbookCd
      , Long venderNo, String venderNm, String historyDesc
      , String materialCd, Double weightGram, Integer quantity
      , Integer unitPrice, String inptId
  ){
    this.cashNo = cashNo;
    this.regDt = regDt;
    this.storeCd = storeCd;
    this.cashTypeCd = cashTypeCd;
    this.cashTypeCd2 = cashTypeCd2;
    this.bankbookCd = bankbookCd;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.historyDesc = historyDesc;
    this.materialCd = materialCd;
    this.weightGram = weightGram;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.inptId = inptId;
  }
}
