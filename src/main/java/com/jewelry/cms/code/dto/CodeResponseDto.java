package com.jewelry.cms.code.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeResponseDto extends CommonVO {

  private String cdId;
  private String cdNm;
  private Integer cdOrd;
  private String upCdId;
  private Integer cdDepth;
  private String useYn;
  private String inptId;
  private String inptNm;
  private LocalDateTime inptDt;
  private Long childCnt;

  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
      , String upCdId, Integer cdDepth, String useYn
      , String inptId, String inptNm, LocalDateTime inptDt
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
    this.upCdId = upCdId;
    this.cdDepth = cdDepth;
    this.useYn = useYn;
    this.inptId = inptId;
    this.inptNm = inptNm;
    this.inptDt = inptDt;
  }

  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
      , String upCdId, Integer cdDepth, String useYn
      , String inptId, LocalDateTime inptDt
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
    this.upCdId = upCdId;
    this.cdDepth = cdDepth;
    this.useYn = useYn;
    this.inptId = inptId;
    this.inptDt = inptDt;
  }

  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
      , String upCdId, Integer cdDepth, String useYn
      , String inptId, LocalDateTime inptDt
      , Long childCnt
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
    this.upCdId = upCdId;
    this.cdDepth = cdDepth;
    this.useYn = useYn;
    this.inptId = inptId;
    this.inptDt = inptDt;
    this.childCnt = childCnt;
  }

  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
  }
}
