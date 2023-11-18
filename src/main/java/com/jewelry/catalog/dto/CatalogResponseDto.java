package com.jewelry.catalog.dto;

import com.jewelry.common.domain.CommonVO;
import com.jewelry.file.dto.FileResponseDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogResponseDto extends CommonVO {

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

  private String venderNm;

  private String filePath;
  private String originNm;
  private String fileNm;

  private List<FileResponseDto> files;

  private List<CatalogStoneResponseDto> catalogStones;

  @QueryProjection
  public CatalogResponseDto(
      Long catalogNo, Long venderNo, String modelId
      , String modelNm, String stddMaterialCd, String stddWeight
      , String stddColorCd, String stddSize, String odrNotice
      , String regDt, Integer basicIdst, Integer mainPrice
      , Integer subPrice, Integer totalPrice, String venderNm
  ){
    this.catalogNo = catalogNo;
    this.venderNo = venderNo;
    this.modelId = modelId;
    this.modelNm = modelNm;
    this.stddMaterialCd = stddMaterialCd;
    this.stddWeight = stddWeight;
    this.stddColorCd = stddColorCd;
    this.stddSize = stddSize;
    this.odrNotice = odrNotice;
    this.regDt = regDt;
    this.basicIdst = basicIdst;
    this.mainPrice = mainPrice;
    this.subPrice = subPrice;
    this.totalPrice = totalPrice;
    this.venderNm = venderNm;
  }
  @QueryProjection
  public CatalogResponseDto (
      Long catalogNo, Long venderNo, String modelId, String modelNm, String stddMaterialCd
      , String stddWeight, String stddColorCd, String stddSize, Integer basicIdst
      , String venderNm
      , String filePath, String originNm, String fileNm
  ){
    this.catalogNo = catalogNo;
    this.venderNo = venderNo;
    this.modelId = modelId;
    this.modelNm = modelNm;
    this.stddMaterialCd = stddMaterialCd;
    this.stddWeight = stddWeight;
    this.stddColorCd = stddColorCd;
    this.stddSize = stddSize;
    this.basicIdst = basicIdst;
    this.venderNm = venderNm;
    this.filePath = filePath;
    this.originNm = originNm;
    this.fileNm = fileNm;
  }
}
