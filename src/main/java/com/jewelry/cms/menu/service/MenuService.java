package com.jewelry.cms.menu.service;

import java.util.List;

import com.jewelry.cms.menu.domain.MenuTO;
import com.jewelry.cms.menu.domain.MenuVO;

public interface MenuService {
	
	List<MenuVO> selectMenuList(MenuTO to);
	
	List<MenuVO> selectMenuListAll(MenuTO to);

}
