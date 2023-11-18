package com.jewelry.sale.entity;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "v_sale")
@Immutable
@Getter
@IdClass(SaleId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
  @Id
  private Long saleNo;
  @Id
  private String saleType;
  private String storeCd;
  private LocalDateTime saleDt;
  private String saleType2;
  private String saleDay;
  private Long customerNo;
  private String customerNm;
  private Long catalogNo;
  private String modelId;
  private Integer realPchGoldPrice;
  private String materialCd;
  private Double perWeightGram;
  private String saleDesc;
  private String mainStoneType;
  private String subStoneType;
  private Integer quantity;
  private Integer purchasePrice;
  private Integer consumerPrice;
  private Integer salePrice;
  private String recPayTypeCd;
  private Integer cardPrice;
  private Integer cashPrice;
  private Integer maintPrice;
  private Integer pntPrice;
  private Integer etcPrice;
  private Integer accuPnt;
}

