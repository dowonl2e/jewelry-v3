package com.jewelry.repair.entity;

import com.jewelry.file.entity.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_repair")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Repair {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long repairNo;
  private String repairNm;
  private LocalDateTime repairReqDt;
  private LocalDateTime repairDueDt;
  private LocalDateTime repairResDt;
  private String repairDesc;
  private Long customerNo;
  private String customerNm;
  private String customerCel;
  private String delYn = "N";
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

//  @OneToMany(mappedBy = "repair")
//  private List<File> files = new ArrayList<>();

  @Builder
  public Repair(
      Long repairNo, String repairNm, LocalDateTime repairReqDt
      , LocalDateTime repairDueDt, LocalDateTime repairResDt
      , String repairDesc, Long customerNo, String customerNm
      , String customerCel, String inptId
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
    this.inptId = inptId;
  }
}
