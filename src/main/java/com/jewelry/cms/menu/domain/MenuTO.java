package com.jewelry.cms.menu.domain;

import com.jewelry.common.domain.CommonTO;

public class MenuTO extends CommonTO {

	private String menu_id;
	private String menu_nm;
	private String menu_link;
	private String menu_ord;
	private String up_menu_id;
	private String use_yn;
	private Integer menu_depth;
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getMenu_link() {
		return menu_link;
	}
	public void setMenu_link(String menu_link) {
		this.menu_link = menu_link;
	}
	public String getMenu_ord() {
		return menu_ord;
	}
	public void setMenu_ord(String menu_ord) {
		this.menu_ord = menu_ord;
	}
	public String getUp_menu_id() {
		return up_menu_id;
	}
	public void setUp_menu_id(String up_menu_id) {
		this.up_menu_id = up_menu_id;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public Integer getMenu_depth() {
		return menu_depth;
	}
	public void setMenu_depth(Integer menu_depth) {
		this.menu_depth = menu_depth;
	}
	
	
	
}
