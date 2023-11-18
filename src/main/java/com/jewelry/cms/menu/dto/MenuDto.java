package com.jewelry.cms.menu.dto;

import com.jewelry.cms.menu.entity.Menu;
import com.jewelry.common.dto.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto extends CommonDto {

  private String menuId;
  private String menuNm;
  private String menuLink;
  private Integer menuOrd;
  private String upMenuId;
  private Integer menuDepth;
  private String useYn;

  @Builder
  public Menu toEntity(){
    return Menu.builder()
        .menuId(this.menuId)
        .menuNm(this.menuNm)
        .menuLink(this.menuLink)
        .menuOrd(this.menuOrd)
        .upMenuId(this.upMenuId)
        .menuDepth(this.menuDepth)
        .useYn(this.useYn)
        .inptId(super.getInptId())
        .build();
  }
}
