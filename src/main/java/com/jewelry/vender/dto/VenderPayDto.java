package com.jewelry.vender.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.util.Utils;
import com.jewelry.vender.entity.VenderPay;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class VenderPayDto extends CommonDto {

  private Long payNo;
  private Long venderNo;
  private String regDt;
  private String storeCd;
  private Double exptGoldGram;
  private Integer exptPayPrice;
  private Double prgGoldGram;
  private Integer prgPayPrice;
  private String payEtc;
  private String delYn = "N";
  private Long[] venderPayNoArr;

  public VenderPay toEntity(){
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return VenderPay.builder()
        .payNo(this.payNo)
        .venderNo(this.venderNo)
        .regDt(Utils.convertLocalDateTime(this.regDt))
        .storeCd(this.storeCd)
        .exptGoldGram(this.exptGoldGram)
        .exptPayPrice(this.exptPayPrice)
        .prgGoldGram(this.prgGoldGram)
        .prgPayPrice(this.prgPayPrice)
        .payEtc(this.payEtc)
        .delYn(this.delYn)
        .inptId(super.getInptId())
        .build();
  }
}
