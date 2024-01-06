package com.jewelry.order.document;

import com.jewelry.order.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderSearchDoc {

  @Id
  private Long order_no;
  private String order_type;
  private LocalDateTime receipt_dt;
  private LocalDateTime expected_ord_dt;
  private String store_cd;
  private Long customer_no;
  private String customer_nm;
  private String order_step;
  private Long catalog_no;
  private String model_id;
  private Long vender_no;
  private String vender_nm;
  private String material_cd;
  private String size;
  private String color_cd;
  private Integer quantity;
  private String main_stone_type;
  private String sub_stone_type;
  private String order_desc;
  private String file_path;
  private String file_nm;

  public OrderResponseDto toResponse(){
    return new OrderResponseDto(
        this.order_no, this.order_type, this.receipt_dt,
        this.expected_ord_dt, this.store_cd, this.customer_no,
        this.customer_nm, this.order_step, this.catalog_no,
        this.model_id, this.vender_no, this.vender_nm,
        this.material_cd, this.size, this.color_cd,
        this.quantity, this.main_stone_type, this.sub_stone_type,
        this.order_desc, this.file_path, this.file_nm
    );
  }
}
