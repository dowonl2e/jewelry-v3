package com.jewelry.customer.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.customer.entity.Customer;
import com.jewelry.util.Utils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerDto extends CommonDto {

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
  private String regDt;

  private Long[] customerNoArr;

  public Customer toEntity(){
    return Customer.builder()
        .customerNo(this.customerNo)
        .storeCd(this.storeCd)
        .contractCd(this.contractCd)
        .zipcode(this.zipcode)
        .address1(this.address1)
        .address2(this.address2)
        .etc(this.etc)
        .contractorNm(this.contractorNm)
        .contractorGen(this.contractorGen)
        .contractorCel(this.contractorCel)
        .contractorBirth(this.contractorBirth)
        .contractorLunar(this.contractorLunar)
        .contractorEmail(this.contractorEmail)
        .regDt(Utils.convertLocalDateTime(this.regDt))
        .inptId(super.getInptId())
        .build();
  }
}
