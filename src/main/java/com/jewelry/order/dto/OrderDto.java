package com.jewelry.order.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.order.entity.Order;
import com.jewelry.util.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto extends CommonDto {

  private Long orderNo;
  private String orderType;
  private String storeCd;
  private String receiptDt;
  private String expectedOrdDt;
  private Long customerNo;
  private String customerNm;
  private String customerCel;
  private String orderStep;
  private String serialId;
  private Long catalogNo;
  private String modelId;
  private Long venderNo;
  private String venderNm;
  private String materialCd;
  private String colorCd;
  private Integer quantity;
  private String mainStoneType;
  private String subStoneType;
  private String size;
  private Long stockNo;
  private String orderDesc;
  private MultipartFile orderFile;
  private Long[] orderNoArr;
  private String[] orderTypeArr;

  private String[] serialIdArr;
  private Long[] catalogNoArr;
  private String[] modelIdArr;
  private Long[] venderNoArr;
  private String[] venderNmArr;
  private String[] materialCdArr;
  private String[] colorCdArr;
  private Integer[] quantityArr;
  private String[] mainStoneTypeArr;
  private String[] subStoneTypeArr;
  private String[] sizeArr;
  private String[] orderDescArr;

  public Order toEntity(){
    return Order.builder()
        .orderNo(this.orderNo)
        .orderType(this.orderType)
        .storeCd(this.storeCd)
        .receiptDt(Utils.convertLocalDateTime(this.receiptDt))
        .expectedOrdDt(Utils.convertLocalDateTime(this.expectedOrdDt))
        .customerNo(this.customerNo)
        .customerNm(this.customerNm)
        .customerCel(this.customerCel)
        .orderStep(this.orderStep)
        .serialId(this.serialId)
        .catalogNo(this.catalogNo)
        .modelId(this.modelId)
        .venderNo(this.venderNo)
        .venderNm(this.venderNm)
        .materialCd(this.materialCd)
        .colorCd(this.colorCd)
        .quantity(this.quantity)
        .mainStoneType(this.mainStoneType)
        .subStoneType(this.subStoneType)
        .size(this.size)
        .stockNo(this.stockNo)
        .orderDesc(this.orderDesc)
        .inptId(super.getInptId())
        .build();
  }
}
