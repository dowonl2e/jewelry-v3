package com.jewelry.cms.menu.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

  @Id
  private String menuId;
  private String menuNm;
  private String menuLink;
  private Integer menuOrd;
  private String upMenuId;
  private Integer menuDepth;
  private String useYn;
  private String inptId;
  private LocalDateTime inptDt = LocalDateTime.now();
  private String updtId;
  private LocalDateTime updtDt;

  @OneToMany(mappedBy = "menu")
  private List<MenuAuth> menuAuths = new ArrayList<>();

  @Builder
  public Menu(
      String menuId, String menuNm, String menuLink
      , Integer menuOrd, String upMenuId, Integer menuDepth
      , String useYn, String inptId
  ){
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.menuLink = menuLink;
    this.menuOrd = menuOrd;
    this.upMenuId = upMenuId;
    this.menuDepth = menuDepth;
    this.useYn = useYn;
    this.inptId = inptId;
  }
}
