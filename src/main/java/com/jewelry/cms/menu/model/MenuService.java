package com.jewelry.cms.menu.model;

import com.jewelry.cms.menu.dto.MenuDto;
import com.jewelry.cms.menu.dto.MenuResponseDto;
import com.jewelry.cms.menu.entity.MenuRepository;
import com.jewelry.cms.menu.entity.MenuRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final MenuRepositoryImpl menuRepositoryImpl;

  public List<MenuResponseDto> findAllByUserId(final MenuDto menuDto){
    return menuRepositoryImpl.getMenusByUserId(menuDto);
  }

  public List<MenuResponseDto> findAllByDepth(final MenuDto menuDto){
    return menuRepositoryImpl.getMenusByDepth(menuDto);
  }
}
