package com.jewelry.sale.dto;

import com.jewelry.common.dto.CommonDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDto extends CommonDto {

  private Long[] saleNoArr;

  private String[] saleArr;

  private Long customerNo;
  private String customerNm;
  private String customerCel;

  private String saleDt;

}
