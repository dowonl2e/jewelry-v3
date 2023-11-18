package com.jewelry.customer.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerResponseDto extends CommonVO {
  private Long customerNo;
  private String storeCd;
  private String contractCd;
  private String zipcode;
  private String address1;
  private String address2;
  private String etc;
  private String contractorNm;
  private String contractorGen;
  private String contractorCel;
  private String contractorBirth;
  private String contractorLunar;
  private String contractorEmail;
  private LocalDateTime regDt;

  private Integer orderCnt;
  private Integer repairCnt;
  private Integer reserveCnt;
  private Integer saleCnt;
  private Integer salePrice;

  @QueryProjection
  public CustomerResponseDto(
      Long customerNo, String contractCd, String etc
      , String contractorNm, String contractorCel, LocalDateTime regDt
      , Integer orderCnt, Integer repairCnt, Integer reserveCnt
      , Integer saleCnt, Integer salePrice
  ){
    this.customerNo = customerNo;
    this.contractCd = contractCd;
    this.etc = etc;
    this.contractorNm = contractorNm;
    this.contractorCel = contractorCel;
    this.regDt = regDt;
    this.orderCnt = orderCnt;
    this.repairCnt = repairCnt;
    this.reserveCnt = reserveCnt;
    this.saleCnt = saleCnt;
    this.salePrice = salePrice;
  }

  @QueryProjection
  public CustomerResponseDto(
      Long customerNo, String contractCd, String etc
      , String contractorNm, String contractorCel, LocalDateTime regDt
      , Integer orderCnt, Integer repairCnt, Integer reserveCnt
      , Integer salePrice
  ){
    this.customerNo = customerNo;
    this.contractCd = contractCd;
    this.etc = etc;
    this.contractorNm = contractorNm;
    this.contractorCel = contractorCel;
    this.regDt = regDt;
    this.orderCnt = orderCnt;
    this.repairCnt = repairCnt;
    this.reserveCnt = reserveCnt;
    this.salePrice = salePrice;
  }

  @QueryProjection
  public CustomerResponseDto(
      Long customerNo, String storeCd, String contractCd
      , String zipcode, String address1, String address2
      , String etc, String contractorNm, String contractorGen
      , String contractorCel, String contractorBirth, String contractorLunar
      , String contractorEmail, LocalDateTime regDt, String inptId
  ){
    this.customerNo = customerNo;
    this.storeCd = storeCd;
    this.contractCd = contractCd;
    this.zipcode = zipcode;
    this.address1 = address1;
    this.address2 = address2;
    this.etc = etc;
    this.contractorNm = contractorNm;
    this.contractorGen = contractorGen;
    this.contractorCel = contractorCel;
    this.contractorBirth = contractorBirth;
    this.contractorLunar = contractorLunar;
    this.contractorEmail = contractorEmail;
    this.regDt = regDt;
    super.setInptId(inptId);
  }
}
