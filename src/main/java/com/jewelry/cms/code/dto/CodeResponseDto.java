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
@NoArgsConstructor
public class CodeResponseDto extends CommonVO {

  private String cdId;
  private String cdNm;
  private Integer cdOrd;
  private String upCdId;
  private Integer cdDepth;
  private String cdDesc;
  private String useYn;
  private String inptId;
  private String inptNm;
  private LocalDateTime inptDt;
  private Long childCnt;

  /**
   * 목록 조회에 사용
   * @param cdId
   * @param cdNm
   * @param cdOrd
   * @param upCdId
   * @param cdDepth
   * @param useYn
   * @param inptId
   * @param inptNm
   * @param inptDt
   */
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

  /**
   * 상세 내역 조회에 사용
   * @param cdId
   * @param cdNm
   * @param cdOrd
   * @param upCdId
   * @param cdDepth
   * @param useYn
   * @param inptId
   * @param inptDt
   * @param childCnt
   */
  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
      , String upCdId, Integer cdDepth, String cdDesc
      , String useYn, String inptId, LocalDateTime inptDt
      , Long childCnt
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
    this.upCdId = upCdId;
    this.cdDepth = cdDepth;
    this.cdDesc = cdDesc;
    this.useYn = useYn;
    this.inptId = inptId;
    this.inptDt = inptDt;
    this.childCnt = childCnt;
  }

  /**
   * 하위 코드 순번 조회에 사용
   * @param cdId
   * @param cdNm
   * @param cdOrd
   */
  @QueryProjection
  public CodeResponseDto(
      String cdId, String cdNm, Integer cdOrd
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
  }
}
