package com.jewelry.vender.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenderPayResponseDto extends CommonVO {

  private Long payNo;
  private Long venderNo;
  private String venderNm;
  private LocalDateTime regDt;
  private String storeCd;
  private Double exptGoldGram;
  private Integer exptPayPrice;
  private Double prgGoldGram;
  private Integer prgPayPrice;
  private String payEtc;
  private String delYn;

  @QueryProjection
  public VenderPayResponseDto(
      Long payNo, Long venderNo, String venderNm
      , LocalDateTime regDt, Double exptGoldGram, Integer exptPayPrice
      , Double prgGoldGram, Integer prgPayPrice, String payEtc
  ){
    this.payNo = payNo;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.regDt = regDt;
    this.exptGoldGram = exptGoldGram;
    this.exptPayPrice = exptPayPrice;
    this.prgGoldGram = prgGoldGram;
    this.prgPayPrice = prgPayPrice;
    this.payEtc = payEtc;
  }
  @QueryProjection
  public VenderPayResponseDto(
      Long payNo, Long venderNo, String venderNm
      , LocalDateTime regDt, String storeCd, Double exptGoldGram
      , Integer exptPayPrice, Double prgGoldGram, Integer prgPayPrice
      , String payEtc
  ){
    this.payNo = payNo;
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.regDt = regDt;
    this.storeCd = storeCd;
    this.exptGoldGram = exptGoldGram;
    this.exptPayPrice = exptPayPrice;
    this.prgGoldGram = prgGoldGram;
    this.prgPayPrice = prgPayPrice;
    this.payEtc = payEtc;
  }
}
