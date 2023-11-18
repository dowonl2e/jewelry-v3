package com.jewelry.cms.menu.entity;

import com.jewelry.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_auth_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(MenuAuthID.class)
public class MenuAuth {

  @Id
  private String userId;
  @Id
  private String menuId;
  private String accessAuth;
  private String writeAuth;
  private String viewAuth;
  private String modifyAuth;
  private String removeAuth;
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "menuId", referencedColumnName = "menuId"
      , insertable = false, updatable = false
  )
  private Menu menu;

  @Builder
  public MenuAuth(
      String userId, String menuId,
      String accessAuth, String writeAuth, String viewAuth,
      String modifyAuth, String removeAuth,
      String inptId
  ){
    this.userId = userId;
    this.menuId = menuId;
    this.accessAuth = accessAuth;
    this.writeAuth = writeAuth;
    this.viewAuth = viewAuth;
    this.modifyAuth = modifyAuth;
    this.removeAuth = removeAuth;
    this.inptId = inptId;
  }
}
