package com.jewelry.cms.menu.service;

import com.jewelry.cms.menu.domain.MenuAuthTO;
import com.jewelry.cms.menu.domain.MenuAuthVO;
import com.jewelry.user.domain.UserTO;

import java.util.List;
import java.util.Map;

public interface MenuAuthService {
		
	Map<String, Object> findAllManager(UserTO to);
	
	Map<String, Object> findAllMenuAuth(MenuAuthTO to);

	String updateMenusAuth(MenuAuthTO to);
	
	String updateMenuAuth(MenuAuthTO to);
	
	List<MenuAuthVO> findUserMenusAuth(MenuAuthTO to);

	MenuAuthVO findUserMenuAuth(MenuAuthTO to);
}
