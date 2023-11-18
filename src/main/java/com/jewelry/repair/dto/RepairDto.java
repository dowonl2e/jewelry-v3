package com.jewelry.repair.dto;

import com.jewelry.common.dto.CommonDto;
import com.jewelry.repair.entity.Repair;
import com.jewelry.util.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RepairDto extends CommonDto {

  private Long repairNo;
  private String repairNm;
  private String repairReqDt;
  private String repairDueDt;
  private String repairResDt;
  private String repairDesc;
  private Long customerNo;
  private String customerNm;
  private String customerCel;
  private MultipartFile repairFile;
  private Long[] repairNoArr;
  public Repair toEntity(){
    return Repair.builder()
        .repairNo(this.repairNo)
        .repairNm(this.repairNm)
        .repairReqDt(Utils.convertLocalDateTime(this.repairReqDt))
        .repairDueDt(Utils.convertLocalDateTime(this.repairDueDt))
        .repairResDt(Utils.convertLocalDateTime(this.repairResDt))
        .repairDesc(this.repairDesc)
        .customerNo(this.customerNo)
        .customerNm(this.customerNm)
        .customerCel(this.customerCel)
        .inptId(super.getInptId())
        .build();
  }
}
