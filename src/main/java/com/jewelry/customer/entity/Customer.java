package com.jewelry.customer.entity;

import com.jewelry.order.entity.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String delYn = "N";
  private LocalDateTime regDt;
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @OneToMany(mappedBy = "customer")
  private List<Order> orders = new ArrayList<>();

  @Builder
  public Customer(
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
    this.inptId = inptId;
  }
}
