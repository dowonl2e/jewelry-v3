package com.jewelry.cms.menu.domain;

import com.jewelry.common.domain.CommonVO;

import java.util.List;

public class MenuAuthVO extends CommonVO {

	private String userId;
	private String menuId;
	private String upMenuId;
	private String menuNm;
	private String accessAuth;
	private String writeAuth;
	private String viewAuth;
	private String modifyAuth;
	private String removeAuth;
	
	private List<MenuAuthVO> list;

	public MenuAuthVO(){}

	public MenuAuthVO(String accessAuth, String writeAuth, String viewAuth, String modifyAuth, String removeAuth){
		this.accessAuth = accessAuth;
		this.writeAuth = writeAuth;
		this.viewAuth = viewAuth;
		this.modifyAuth = modifyAuth;
		this.removeAuth = removeAuth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getAccessAuth() {
		return accessAuth;
	}

	public void setAccessAuth(String accessAuth) {
		this.accessAuth = accessAuth;
	}

	public String getWriteAuth() {
		return writeAuth;
	}

	public void setWriteAuth(String writeAuth) {
		this.writeAuth = writeAuth;
	}

	public String getViewAuth() {
		return viewAuth;
	}

	public void setViewAuth(String viewAuth) {
		this.viewAuth = viewAuth;
	}

	public String getModifyAuth() {
		return modifyAuth;
	}

	public void setModifyAuth(String modifyAuth) {
		this.modifyAuth = modifyAuth;
	}

	public String getRemoveAuth() {
		return removeAuth;
	}

	public void setRemoveAuth(String removeAuth) {
		this.removeAuth = removeAuth;
	}

	public List<MenuAuthVO> getList() {
		return list;
	}

	public void setList(List<MenuAuthVO> list) {
		this.list = list;
	}

	public String getUpMenuId() {
		return upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}
	
	
}
