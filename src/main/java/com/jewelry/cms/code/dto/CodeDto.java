package com.jewelry.cms.code.dto;

import com.jewelry.cms.code.entity.Code;
import com.jewelry.common.dto.CommonDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CodeDto extends CommonDto {

  private String cdId;
  private String cdNm;
  private Integer cdOrd;
  private String upCdId;
  private Integer cdDepth;
  private String useYn;
  private String[] upCdIdArr;
  public Code toEntity(){
    return Code.builder()
        .cdId(this.cdId)
        .cdNm(this.cdNm)
        .cdOrd(this.cdOrd)
        .upCdId(this.upCdId)
        .cdDepth(this.cdDepth)
        .useYn(this.useYn)
        .inptId(super.getInptId())
        .build();
  }

}
