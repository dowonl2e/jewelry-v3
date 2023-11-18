package com.jewelry.sale.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class SaleId implements Serializable {
  private Long saleNo;
  private String saleType;
}
