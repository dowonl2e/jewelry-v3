package com.jewelry.vender.entity;

import com.jewelry.catalog.entity.Catalog;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_vender")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vender {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @OneToMany(mappedBy = "vender")
  private List<Catalog> catalogs = new ArrayList<>();

  @OneToMany(mappedBy = "vender")
  private List<Catalog> venderPays = new ArrayList<>();

  @Builder
  public Vender(
    Long venderNo, String venderNm, String businessNm, String agentCel, String vatCd
    , String meltCd, String venderFax, String venderCel1, String venderCel2
    , String managerNm, String managerCel, String managerEmail, String etc
    , String commerce, String delYn, String inptId){
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
    this.delYn = delYn;
    this.inptId = inptId;
  }

}
