package com.jewelry.cms.menu.dto;

import com.jewelry.cms.menu.entity.MenuAuth;
import com.jewelry.common.dto.CommonDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
public class MenuAuthDto extends CommonDto {

  private String accessAuth = "N";
  private String writeAuth = "N";
  private String viewAuth = "N";
  private String modifyAuth = "N";
  private String removeAuth = "N";

  private String[] menuIdArr;
  private String[] accessAuthArr;
  private String[] writeAuthArr;
  private String[] viewAuthArr;
  private String[] modifyAuthArr;
  private String[] removeAuthArr;

  public MenuAuth toEntity(){
    return MenuAuth.builder()
        .userId(super.getUserId())
        .menuId(super.getMenuId())
        .accessAuth(this.accessAuth)
        .writeAuth(this.writeAuth)
        .viewAuth(this.viewAuth)
        .modifyAuth(this.modifyAuth)
        .removeAuth(this.removeAuth)
        .inptId(super.getInptId())
        .build();
  }
}
