package com.jewelry.order.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT CUSTOMER_NO, COUNT(CUSTOMER_NO) AS ORDER_CNT, SUM(IF(ORDER_STEP='B',1,0)) AS RESERVE_CNT " +
        "FROM TB_ORDER " +
        "WHERE DEL_YN = 'N' " +
        "GROUP BY CUSTOMER_NO"
)
@Immutable
public class OrderCustomerCnt {

  @Id
  private Long customerNo;

  private Integer orderCnt;
  private Integer reserveCnt;
}
