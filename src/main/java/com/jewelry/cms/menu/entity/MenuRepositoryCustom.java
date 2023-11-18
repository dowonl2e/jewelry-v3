package com.jewelry.cms.menu.entity;

import com.jewelry.cms.menu.dto.MenuDto;
import com.jewelry.cms.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuRepositoryCustom {

  List<MenuResponseDto> getMenusByUserId(final MenuDto menuDto);

  List<MenuResponseDto> getMenusByDepth(final MenuDto menuDto);

}
