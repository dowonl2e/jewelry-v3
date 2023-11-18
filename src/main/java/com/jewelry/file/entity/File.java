package com.jewelry.file.entity;

import com.jewelry.catalog.entity.Catalog;
import com.jewelry.repair.entity.Repair;
import lombok.*;
import org.apache.ibatis.annotations.One;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String delYn= "N";

  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(
//      name = "refNo", referencedColumnName = "repairNo"
//      , insertable = false
//      , updatable = false
//  )
//  private Repair repair;

  @Builder
  public File(
      Long fileNo, Long refNo, String refInfo, String filePath, String originNm, String fileNm
      , Integer fileOrd, String fileExt, Long fileSize, String versionId, String inptId){
    this.fileNo = fileNo;
    this.refNo = refNo;
    this.refInfo = refInfo;
    this.filePath = filePath;
    this.originNm = originNm;
    this.fileNm = fileNm;
    this.fileOrd = fileOrd;
    this.fileExt = fileExt;
    this.fileSize = fileSize;
    this.versionId = versionId;
    this.inptId = inptId;
  }

}
