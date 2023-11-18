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
public class VenderResponseDto extends CommonVO {
  private Long venderNo;
  private String venderNm;
  private String businessNm;
  private String agentCel;
  private String vatCd;
  private String meltCd;
  private String venderFax;
  private String venderCel1;
  private String venderCel2;
  private String managerNm;
  private String managerCel;
  private String managerEmail;
  private String etc;
  private String commerce;
  private String delYn;

  @QueryProjection
  public VenderResponseDto(
      Long venderNo, String venderNm, String businessNm
      , String agentCel, String vatCd, String meltCd
      , String venderFax, String venderCel1
      , String managerNm, String managerCel, String etc
      , LocalDateTime inptDt
  ){
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.businessNm = businessNm;
    this.agentCel = agentCel;
    this.vatCd = vatCd;
    this.meltCd = meltCd;
    this.venderFax = venderFax;
    this.venderCel1 = venderCel1;
    this.managerNm = managerNm;
    this.managerCel = managerCel;
    this.etc = etc;
    super.inptDt = inptDt;
  }

  @QueryProjection
  public VenderResponseDto(
      Long venderNo, String venderNm, String businessNm, String agentCel, String vatCd
      , String meltCd, String venderFax, String venderCel1, String venderCel2
      , String managerNm, String managerCel, String managerEmail, String etc
      , String commerce
  ){
    this.venderNo = venderNo;
    this.venderNm = venderNm;
    this.businessNm = businessNm;
    this.agentCel = agentCel;
    this.vatCd = vatCd;
    this.meltCd = meltCd;
    this.venderFax = venderFax;
    this.venderCel1 = venderCel1;
    this.venderCel2 = venderCel2;
    this.managerNm = managerNm;
    this.managerCel = managerCel;
    this.managerEmail = managerEmail;
    this.etc = etc;
    this.commerce = commerce;
  }
}
