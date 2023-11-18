package com.jewelry.sale.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT CUSTOMER_NO, COUNT(CUSTOMER_NO) AS SALE_CNT, SUM(SALE_PRICE) AS SALE_PRICE " +
        "FROM TB_STOCK " +
        "WHERE IFNULL(CUSTOMER_NO,0) > 0 " +
        "AND SALE_YN = 'Y' " +
        "AND DEL_YN = 'N' " +
        "GROUP BY CUSTOMER_NO"
)
@Immutable
public class SaleCustomerCnt {

  @Id
  private Long customerNo;

  private Integer saleCnt;
  private Integer salePrice;
}
