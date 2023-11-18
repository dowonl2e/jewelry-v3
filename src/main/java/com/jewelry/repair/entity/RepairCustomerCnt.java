package com.jewelry.repair.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT CUSTOMER_NO, COUNT(CUSTOMER_NO) AS REPAIR_CNT " +
        "FROM TB_REPAIR " +
        "WHERE DEL_YN = 'N' " +
        "GROUP BY CUSTOMER_NO"
)
@Immutable
public class RepairCustomerCnt {
  @Id
  private Long customerNo;

  private Integer repairCnt;
}
