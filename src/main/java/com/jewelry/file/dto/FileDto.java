package com.jewelry.file.dto;

import com.jewelry.file.entity.File;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
public class FileDto {

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
  private String inptId;
  private String updtId;

  public FileDto(){}

  public FileDto(Long refNo, String refInfo){
    this.refNo = refNo;
    this.refInfo = refInfo;
  }
  public FileDto(Long refNo, String refInfo, Integer fileOrd){
    this(refNo, refInfo);
    this.fileOrd = fileOrd;
  }
  public File toEntity(){
    return File.builder()
        .fileNo(this.fileNo)
        .refNo(this.refNo)
        .refInfo(this.refInfo)
        .filePath(this.filePath)
        .originNm(this.originNm)
        .fileNm(this.fileNm)
        .fileOrd(this.fileOrd)
        .fileExt(this.fileExt)
        .fileSize(this.fileSize)
        .versionId(this.versionId)
        .inptId(this.inptId)
        .build();
  }
}
