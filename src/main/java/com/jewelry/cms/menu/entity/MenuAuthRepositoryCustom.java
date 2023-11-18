package com.jewelry.cms.menu.entity;

import com.jewelry.cms.menu.dto.MenuAuthDto;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;

import java.util.List;

public interface MenuAuthRepositoryCustom {

  List<MenuAuthResponseDto> getMenuAuths(final String userId);
  long removeByUserId(final String userId);
  long remove(final String userId, final String menuId);
  long updateMenuAuth(final MenuAuthDto menuAuthDto);

  MenuAuthResponseDto getMenuAuth(final MenuAuthDto menuAuthDto);

}
