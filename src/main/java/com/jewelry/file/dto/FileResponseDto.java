package com.jewelry.file.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResponseDto {
  private Long fileNo;
  private Long refNo;
  private String refInfo;
  private String filePath;
  private String originNm;
  private String fileNm;
  private Integer fileOrd;
  private String fileExt;
  private Long fileSize;
  private String versionId;
  private String delYn;

  @QueryProjection
  public FileResponseDto(
    Long fileNo, Long refNo, String refInfo
    , String filePath, String originNm, String fileNm
    , Integer fileOrd, String fileExt, Long fileSize
  ){
    this.fileNo = fileNo;
    this.refNo = refNo;
    this.refInfo = refInfo;
    this.filePath = filePath;
    this.originNm = originNm;
    this.fileNm = fileNm;
    this.fileOrd = fileOrd;
    this.fileExt = fileExt;
    this.fileSize = fileSize;
  }
}
