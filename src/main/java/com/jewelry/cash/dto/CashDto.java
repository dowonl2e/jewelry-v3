package com.jewelry.cash.dto;

import com.jewelry.cash.entity.Cash;
import com.jewelry.common.dto.CommonDto;
import com.jewelry.util.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashDto extends CommonDto {

  private Long cashNo;
  private String regDt;
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

  private Long[] cashNoArr;
  private String[] cashTypeCdArr;
  private String[] cashTypeCd2Arr;
  private String[] bankbookCdArr;
  private String[] venderNmArr;
  private String[] historyDescArr;
  private String[] materialCdArr;
  private Double[] weightGramArr;
  private Integer[] quantityArr;
  private Integer[] unitPriceArr;

  private String today;
  private String yesterday;
  private String befYesterday;

  @Builder
  public Cash toEntity(){
    return Cash.builder()
        .regDt(Utils.convertLocalDateTime(this.regDt))
        .storeCd(this.storeCd)
        .cashTypeCd(this.cashTypeCd)
        .cashTypeCd2(this.cashTypeCd2)
        .bankbookCd(this.bankbookCd)
        .venderNo(this.venderNo)
        .venderNm(this.venderNm)
        .historyDesc(this.historyDesc)
        .materialCd(this.materialCd)
        .weightGram(this.weightGram)
        .quantity(this.quantity)
        .unitPrice(this.unitPrice)
        .inptId(super.getInptId())
        .build();
  }

}
