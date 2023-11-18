package com.jewelry.cash.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CashResponseDto extends CommonVO {

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

  private String regDay;

  private Integer statsOrd;
  private String statsCd;
  private Integer befYesterdayPrice;
  private Integer befYesterdayPrice2;
  private Integer yesterdayPrice;
  private Integer yesterdayPrice2;
  private Integer todayPrice;
  private Integer todayPrice2;

  @QueryProjection
  public CashResponseDto(
      Long cashNo, LocalDateTime regDt, String storeCd
      , String cashTypeCd, String cashTypeCd2, String bankbookCd
      , String venderNm, String historyDesc, String materialCd
      , Double weightGram, Integer quantity, Integer unitPrice
      , String regDay
  ){
    this.cashNo = cashNo;
    this.regDt = regDt;
    this.storeCd = storeCd;
    this.cashTypeCd = cashTypeCd;
    this.cashTypeCd2 = cashTypeCd2;
    this.bankbookCd = bankbookCd;
    this.venderNm = venderNm;
    this.historyDesc = historyDesc;
    this.materialCd = materialCd;
    this.weightGram = weightGram;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.regDay = regDay;
  }

  @QueryProjection
  public CashResponseDto(
      Long cashNo, LocalDateTime regDt, String storeCd
      , String cashTypeCd, String cashTypeCd2, String bankbookCd
      , Long venderNo, String venderNm, String historyDesc
      , String materialCd, Double weightGram, Integer quantity
      , Integer unitPrice
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
  }

  @QueryProjection
  public CashResponseDto(
      Integer statsOrd, String storeCd, String statsCd
      , Integer befYesterdayPrice, Integer yesterdayPrice, Integer todayPrice
  ){
    this.statsOrd = statsOrd;
    this.storeCd = storeCd;
    this.statsCd = statsCd;
    this.befYesterdayPrice = befYesterdayPrice;
    this.yesterdayPrice = yesterdayPrice;
    this.todayPrice = todayPrice;
  }

  @QueryProjection
  public CashResponseDto(
      Integer statsOrd, String storeCd, String statsCd
      , Integer befYesterdayPrice, Integer befYesterdayPrice2
      , Integer yesterdayPrice, Integer yesterdayPrice2
  ){
    this.statsOrd = statsOrd;
    this.storeCd = storeCd;
    this.statsCd = statsCd;
    this.befYesterdayPrice = befYesterdayPrice;
    this.befYesterdayPrice2 = befYesterdayPrice2;
    this.yesterdayPrice = yesterdayPrice;
    this.yesterdayPrice2 = yesterdayPrice2;
  }

  @QueryProjection
  public CashResponseDto(
      String storeCd, String statsCd, Integer todayPrice, Integer todayPrice2
  ){
    this.storeCd = storeCd;
    this.statsCd = statsCd;
    this.todayPrice = todayPrice;
    this.todayPrice2 = todayPrice2;
  }
}
