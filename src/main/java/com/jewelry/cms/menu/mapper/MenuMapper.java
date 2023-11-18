package com.jewelry.cms.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jewelry.cms.menu.domain.MenuTO;
import com.jewelry.cms.menu.domain.MenuVO;

@Mapper
public interface MenuMapper {
	
	List<MenuVO> selectMenuList(MenuTO to);
	
	List<MenuVO> selectMenuListAll(MenuTO to);
}
