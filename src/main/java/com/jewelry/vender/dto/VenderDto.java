package com.jewelry.vender.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.vender.entity.Vender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VenderDto extends CommonDto {
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
  private LocalDateTime inptDt;
  private LocalDateTime updtDt;

  private Long[] venderNoArr;

  public Vender toEntity(){
    return Vender.builder()
        .venderNo(this.venderNo)
        .venderNm(this.venderNm)
        .businessNm(this.businessNm)
        .agentCel(this.agentCel)
        .vatCd(this.vatCd)
        .meltCd(this.meltCd)
        .venderFax(this.venderFax)
        .venderCel1(this.venderCel1)
        .venderCel2(this.venderCel2)
        .managerNm(this.managerNm)
        .managerCel(this.managerCel)
        .managerEmail(this.managerEmail)
        .etc(this.etc)
        .commerce(this.commerce)
        .delYn(this.delYn)
        .inptId(super.getInptId())
        .build();
  }
}
