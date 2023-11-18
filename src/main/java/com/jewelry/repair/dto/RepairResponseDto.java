package com.jewelry.repair.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairResponseDto extends CommonVO {

  private Long repairNo;
  private String repairNm;
  private LocalDateTime repairReqDt;
  private LocalDateTime repairDueDt;
  private LocalDateTime repairResDt;
  private String repairDesc;
  private Long customerNo;
  private String customerNm;
  private String customerCel;

  private String filePath;
  private String originNm;
  private String fileNm;

  @QueryProjection
  public RepairResponseDto(
      Long repairNo, String repairNm, LocalDateTime repairReqDt
      , LocalDateTime repairDueDt, LocalDateTime repairResDt, Long customerNo
      , String customerNm, String filePath, String originNm, String fileNm
  ){
    this.repairNo = repairNo;
    this.repairNm = repairNm;
    this.repairReqDt = repairReqDt;
    this.repairDueDt = repairDueDt;
    this.repairResDt = repairResDt;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.filePath = filePath;
    this.originNm = originNm;
    this.fileNm = fileNm;
  }

  @QueryProjection
  public RepairResponseDto(
      Long repairNo, String repairNm, LocalDateTime repairReqDt
      , LocalDateTime repairDueDt, LocalDateTime repairResDt, String repairDesc
      , Long customerNo, String customerNm, String customerCel
      , String filePath, String fileNm
  ){
    this.repairNo = repairNo;
    this.repairNm = repairNm;
    this.repairReqDt = repairReqDt;
    this.repairDueDt = repairDueDt;
    this.repairResDt = repairResDt;
    this.repairDesc = repairDesc;
    this.customerNo = customerNo;
    this.customerNm = customerNm;
    this.customerCel = customerCel;
    this.filePath = filePath;
    this.fileNm = fileNm;
  }
}
