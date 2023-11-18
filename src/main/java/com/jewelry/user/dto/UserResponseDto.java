package com.jewelry.user.dto;

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
public class UserResponseDto extends CommonVO {

  private String userId;
  private String userPwd;
  private String userName;
  private String userRole;
  private String email;
  private String celnum;
  private String gender;
  private String useYn;

  @QueryProjection
  public UserResponseDto(
      String userId, String userName, String userRole
    , String email, String celnum, String gender
    , LocalDateTime inptDt, String useYn
  ){
    this.userId = userId;
    this.userName = userName;
    this.userRole = userRole;
    this.email = email;
    this.celnum = celnum;
    this.gender = gender;
    super.inptDt = inptDt;
    this.useYn = useYn;
  }

  @QueryProjection
  public UserResponseDto(
      String userId, String userName, String userRole
      , String email, String celnum, String gender
      , String useYn
  ){
    this.userId = userId;
    this.userName = userName;
    this.userRole = userRole;
    this.email = email;
    this.celnum = celnum;
    this.gender = gender;
    this.useYn = useYn;
  }

  @QueryProjection
  public UserResponseDto(
      String userId, String userName, String userRole
      , String gender, String useYn
  ){
    this.userId = userId;
    this.userName = userName;
    this.userRole = userRole;
    this.gender = gender;
    this.useYn = useYn;
  }
}
