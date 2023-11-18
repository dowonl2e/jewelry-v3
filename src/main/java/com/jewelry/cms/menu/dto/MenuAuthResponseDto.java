package com.jewelry.cms.menu.dto;

import com.jewelry.authentication.jwt.values.Role;
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
public class MenuAuthResponseDto extends CommonVO {

  private String menuId;
  private String menuNm;
  private String accessAuth;
  private String writeAuth;
  private String viewAuth;
  private String modifyAuth;
  private String removeAuth;

  private Role userRole;

  public MenuAuthResponseDto(
      String accessAuth, String writeAuth, String viewAuth
      , String modifyAuth, String removeAuth
  ){
    this.accessAuth = accessAuth;
    this.writeAuth = writeAuth;
    this.viewAuth = viewAuth;
    this.modifyAuth = modifyAuth;
    this.removeAuth = removeAuth;
  }

  @QueryProjection
  public MenuAuthResponseDto(
      String menuId, Role userRole
      , String accessAuth, String writeAuth, String viewAuth
      , String modifyAuth, String removeAuth
  ){
    this.menuId = menuId;
    this.userRole = userRole;
    this.accessAuth = accessAuth;
    this.writeAuth = writeAuth;
    this.viewAuth = viewAuth;
    this.modifyAuth = modifyAuth;
    this.removeAuth = removeAuth;
  }

  @QueryProjection
  public MenuAuthResponseDto(
      String userId, String menuId, String menuNm
      , String accessAuth, String writeAuth, String viewAuth
      , String modifyAuth, String removeAuth, String inptId
      , LocalDateTime inptDt
  ){
    super.setUserId(userId);
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.accessAuth = accessAuth;
    this.writeAuth = writeAuth;
    this.viewAuth = viewAuth;
    this.modifyAuth = modifyAuth;
    this.removeAuth = removeAuth;
    super.setInptId(inptId);
    super.setInptDt(inptDt);
  }
}
