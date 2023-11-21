package com.jewelry.sale.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT customer_no, COUNT(customer_no) AS sale_cnt, SUM(sale_price) AS sale_price " +
        "FROM tb_stock " +
        "WHERE IFNULL(customer_no,0) > 0 " +
        "AND sale_yn = 'Y' " +
        "AND del_yn = 'N' " +
        "GROUP BY customer_no"
)
@Immutable
public class SaleCustomerCnt {

  @Id
  private Long customerNo;

  private Integer saleCnt;
  private Integer salePrice;
}
