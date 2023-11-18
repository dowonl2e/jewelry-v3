package com.jewelry.cms.menu.dto;

import com.jewelry.common.domain.CommonVO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuResponseDto extends CommonVO {

  private List<MenuResponseDto> list;
  private List<MenuResponseDto> subList;
  private String menuId;
  private String menuNm;
  private String menuLink;
  private Integer menuOrd;
  private String upMenuId;
  private Integer menuDepth;
  private String useYn;

  private Long childCnt;

  @QueryProjection
  public MenuResponseDto(
      String menuId, String menuNm, String menuLink
      , Integer menuOrd, String upMenuId, String useYn
      , Long childCnt
  ){
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.menuLink = menuLink;
    this.menuOrd = menuOrd;
    this.upMenuId = upMenuId;
    this.useYn = useYn;
    this.childCnt = childCnt;
  }

}
