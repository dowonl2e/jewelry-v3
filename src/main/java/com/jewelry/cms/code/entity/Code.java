package com.jewelry.cms.code.entity;

import com.jewelry.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

  @Id
  private String cdId;
  private String cdNm;
  private Integer cdOrd;
  private String upCdId;
  private Integer cdDepth;
  private String cdDesc;
  private String useYn;
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "inptId", referencedColumnName = "userId"
      , insertable = false, updatable = false
  )
  private UserEntity userEntity;

  @Builder
  public Code(
      String cdId, String cdNm, Integer cdOrd
      , String upCdId, Integer cdDepth, String cdDesc
      , String useYn, String inptId
  ){
    this.cdId = cdId;
    this.cdNm = cdNm;
    this.cdOrd = cdOrd;
    this.upCdId = upCdId;
    this.cdDepth = cdDepth;
    this.cdDesc = cdDesc;
    this.useYn = useYn;
    this.inptId = inptId;
  }
}
