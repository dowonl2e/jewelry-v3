package com.jewelry.vender.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_vender_pay")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenderPay {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long payNo;
  private Long venderNo;
  private LocalDateTime regDt;
  private String storeCd;
  private Double exptGoldGram;
  private Integer exptPayPrice;
  private Double prgGoldGram;
  private Integer prgPayPrice;
  private String payEtc;
  private String delYn;
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

  @Builder
  public VenderPay(
      Long payNo, Long venderNo, LocalDateTime regDt
      , String storeCd, Double exptGoldGram, Integer exptPayPrice
      , Double prgGoldGram, Integer prgPayPrice, String payEtc
      , String delYn, String inptId){
    this.payNo = payNo;
    this.venderNo = venderNo;
    this.regDt = regDt;
    this.storeCd = storeCd;
    this.exptGoldGram = exptGoldGram;
    this.exptPayPrice = exptPayPrice;
    this.prgGoldGram = prgGoldGram;
    this.prgPayPrice = prgPayPrice;
    this.payEtc = payEtc;
    this.delYn = delYn;
    this.inptId = inptId;
  }

}
