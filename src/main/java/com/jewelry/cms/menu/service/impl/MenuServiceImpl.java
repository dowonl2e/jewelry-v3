package com.jewelry.cms.menu.service.impl;

import com.jewelry.cms.menu.domain.MenuTO;
import com.jewelry.cms.menu.domain.MenuVO;
import com.jewelry.cms.menu.mapper.MenuMapper;
import com.jewelry.cms.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
	
	private final MenuMapper menuMapper;

	@Transactional(readOnly = true)
	@Override
	public List<MenuVO> selectMenuList(MenuTO to) {
		return menuMapper.selectMenuList(to);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<MenuVO> selectMenuListAll(MenuTO to) {
		return menuMapper.selectMenuListAll(to);
	}
	
}
