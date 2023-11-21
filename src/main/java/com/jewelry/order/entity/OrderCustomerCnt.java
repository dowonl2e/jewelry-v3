package com.jewelry.order.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT customer_no, COUNT(customer_no) AS order_cnt, SUM(IF(order_step='B',1,0)) AS reserve_cnt " +
        "FROM tb_order " +
        "WHERE del_yn = 'N' " +
        "GROUP BY customer_no"
)
@Immutable
public class OrderCustomerCnt {

  @Id
  private Long customerNo;

  private Integer orderCnt;
  private Integer reserveCnt;
}
