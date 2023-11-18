package com.jewelry.cms.menu.service.impl;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.cms.menu.domain.MenuAuthTO;
import com.jewelry.cms.menu.domain.MenuAuthVO;
import com.jewelry.cms.menu.mapper.MenuAuthMapper;
import com.jewelry.cms.menu.service.MenuAuthService;
import com.jewelry.user.domain.UserTO;
import com.jewelry.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuAuthServiceImpl implements MenuAuthService {

	private final UserMapper userMapper;
	
	private final MenuAuthMapper menuAuthMapper;
		
	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public Map<String, Object> findAllManager(final UserTO to) {
		Map<String, Object> response = new HashMap<>();
		
		to.setTotalcount(userMapper.selectManagerListCount(to));
		response.put("list", userMapper.selectManagerList(to));
		response.put("params", to);
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public Map<String, Object> findAllMenuAuth(final MenuAuthTO to) {

		Map<String, Object> response = new HashMap<>();

		to.setMenu_depth(1);
		List<MenuAuthVO> list = null;
		List<MenuAuthVO> oneAuthList = menuAuthMapper.selectUserAuthMenuList(to);
		if(!CollectionUtils.isEmpty(oneAuthList)) {
			list = new ArrayList<>();

			to.setMenu_depth(2);
			List<MenuAuthVO> twoAuthList = menuAuthMapper.selectUserAuthMenuList(to);
			
			for(MenuAuthVO oneDepthVo : oneAuthList) {
				list.add(oneDepthVo);
				for(MenuAuthVO twoDepthVo : twoAuthList) {
					if(ObjectUtils.nullSafeEquals(oneDepthVo.getMenuId(), twoDepthVo.getUpMenuId())) {
						list.add(twoDepthVo);
					}
				}
			}
		}
		response.put("list", list);
		
		return response;
	}
	
	@Transactional
	@MenuAuthAnt
	@Override
	public String updateMenuAuth(MenuAuthTO to) {
		String result = "fail";
		try {

			Integer existCnt = menuAuthMapper.selectUserAuthMenuExistCnt(to);
			existCnt = existCnt == null ? 0 : existCnt;
			
			int res = existCnt == 0 ? menuAuthMapper.insertUserAuthMenu(to) : menuAuthMapper.updateUserAuthMenu(to);
				
			result = res > 0 ? "success" : "fail";
			
		}
		catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result = "fail";
		}
		return result;
	}

	@Transactional
	@MenuAuthAnt
	@Override
	public String updateMenusAuth(MenuAuthTO to) {
		String result = "fail";
		try {
			int res = 0;
			if(to.getMenuIdArr() != null && to.getMenuIdArr().length > 0) {
				
				List<MenuAuthVO> authList = menuAuthMapper.selectUserAuthMenusWithUserId(to.getUser_id());
				List<MenuAuthTO> insertList = new ArrayList<>();
				List<MenuAuthTO> updateList = new ArrayList<>();
				
				MenuAuthTO insertTo = null;
				MenuAuthTO updateTo = null;
				boolean exist = false;
				int idx = 0;
				for(String menuId : to.getMenuIdArr()) {
					exist = false;
					
					if(!CollectionUtils.isEmpty(authList)) {
						for(MenuAuthVO chkvo : authList) {
							if(ObjectUtils.nullSafeEquals(menuId, chkvo.getMenuId())) {
								exist = true;
								break;
							}
						}
					}
					
					if(exist == false) {
						insertTo = new MenuAuthTO();
						insertTo.setInpt_id(to.getInpt_id());
						insertTo.setUser_id(to.getUser_id());
						insertTo.setMenu_id(menuId);
						insertTo.setAccess_auth(to.getAccessAuthArr()[idx]);
						insertTo.setWrite_auth(to.getWriteAuthArr()[idx]);
						insertTo.setView_auth(to.getViewAuthArr()[idx]);
						insertTo.setModify_auth(to.getModifyAuthArr()[idx]);
						insertTo.setRemove_auth(to.getRemoveAuthArr()[idx]);
						insertList.add(insertTo);
					}
					else {
						updateTo = new MenuAuthTO();
						updateTo.setUpdt_id(to.getUpdt_id());
						updateTo.setUser_id(to.getUser_id());
						updateTo.setMenu_id(menuId);
						updateTo.setAccess_auth(to.getAccessAuthArr()[idx]);
						updateTo.setWrite_auth(to.getWriteAuthArr()[idx]);
						updateTo.setView_auth(to.getViewAuthArr()[idx]);
						updateTo.setModify_auth(to.getModifyAuthArr()[idx]);
						updateTo.setRemove_auth(to.getRemoveAuthArr()[idx]);
						updateList.add(updateTo);
					}
					idx++;
				}
				res += (CollectionUtils.isEmpty(insertList) ? 0 : menuAuthMapper.insertUserAuthMenus(insertList));
				res += (CollectionUtils.isEmpty(updateList) ? 0 : menuAuthMapper.updateUserAuthMenus(updateList));
				
			}
			result = res > 0 ? "success" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result = "fail";
		}
		return result;
	}

	@Transactional(readOnly = true)
	@MenuAuthAnt
	@Override
	public List<MenuAuthVO> findUserMenusAuth(MenuAuthTO to) {
		return menuAuthMapper.selectUserAuthMenus(to);
	}

	@Transactional(readOnly = true)
	@Override
	public MenuAuthVO findUserMenuAuth(MenuAuthTO to) {
		return menuAuthMapper.selectUserAuthMenu(to);
	}
}
