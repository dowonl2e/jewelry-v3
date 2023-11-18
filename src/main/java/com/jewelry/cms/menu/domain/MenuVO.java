package com.jewelry.cms.menu.domain;

import java.util.List;

import com.jewelry.common.domain.CommonVO;

public class MenuVO extends CommonVO {
	
	private List<MenuVO> list;
	private List<MenuVO> subList;

	private String menuid;
	private String menunm;
	private String menulink;
	private String menuord;
	private String upmenuid;
	private String useyn;
	private Integer childcnt;
	
	public List<MenuVO> getList() {
		return list;
	}
	public void setList(List<MenuVO> list) {
		this.list = list;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenunm() {
		return menunm;
	}
	public void setMenunm(String menunm) {
		this.menunm = menunm;
	}
	public String getMenulink() {
		return menulink;
	}
	public void setMenulink(String menulink) {
		this.menulink = menulink;
	}
	public String getMenuord() {
		return menuord;
	}
	public void setMenuord(String menuord) {
		this.menuord = menuord;
	}
	public String getUpmenuid() {
		return upmenuid;
	}
	public void setUpmenuid(String upmenuid) {
		this.upmenuid = upmenuid;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public Integer getChildcnt() {
		return childcnt;
	}
	public void setChildcnt(Integer childcnt) {
		this.childcnt = childcnt;
	}
	public List<MenuVO> getSubList() {
		return subList;
	}
	public void setSubList(List<MenuVO> subList) {
		this.subList = subList;
	}
	
}
