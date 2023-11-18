package com.jewelry.catalog.dto;

import com.jewelry.catalog.entity.Catalog;
import com.jewelry.common.dto.CommonDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CatalogDto extends CommonDto {

  private Long catalogNo;
  private Long venderNo;
  private String modelId;
  private String modelNm;
  private String stddMaterialCd;
  private String stddWeight;
  private String stddColorCd;
  private String stddSize;
  private String odrNotice;
  private String regDt;
  private Integer basicIdst;
  private Integer mainPrice;
  private Integer subPrice;
  private Integer totalPrice;
  private String delNn;

  private MultipartFile catalogFile;

  private Long[] catalogNoArr;

  public Catalog toEntity(){
    return Catalog.builder()
        .venderNo(this.venderNo)
        .modelId(this.modelId)
        .modelNm(this.modelNm)
        .stddMaterialCd(this.stddMaterialCd)
        .stddWeight(this.stddWeight)
        .stddColorCd(this.stddColorCd)
        .stddSize(this.stddSize)
        .odrNotice(this.odrNotice)
        .regDt(this.regDt)
        .basicIdst(this.basicIdst)
        .mainPrice(this.mainPrice)
        .subPrice(this.subPrice)
        .totalPrice(this.totalPrice)
        .inptId(super.getInptId())
        .build();
  }
}
