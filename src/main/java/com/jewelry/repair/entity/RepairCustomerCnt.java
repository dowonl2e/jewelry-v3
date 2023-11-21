package com.jewelry.repair.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect(
    "SELECT customer_no, COUNT(customer_no) AS repair_cnt " +
        "FROM tb_repair " +
        "WHERE del_yn = 'N' " +
        "GROUP BY customer_no"
)
@Immutable
public class RepairCustomerCnt {
  @Id
  private Long customerNo;

  private Integer repairCnt;
}
