package com.jewelry.cms.menu.domain;

import com.jewelry.common.domain.CommonTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuAuthTO extends CommonTO {

	private String user_id;
	private String menu_id;
	private String access_auth;
	private String write_auth;
	private String view_auth;
	private String modify_auth;
	private String remove_auth;
	
	private Integer menu_depth;
	
	private String[] menuIdArr;
	private String[] accessAuthArr;
	private String[] writeAuthArr;
	private String[] viewAuthArr;
	private String[] modifyAuthArr;
	private String[] removeAuthArr;

	public MenuAuthTO() {}

	public MenuAuthTO(String userId) {
		this.user_id = userId;
	}
	
	public MenuAuthTO(String userId, String menuId) {
		this(userId);
		this.menu_id = menuId;
	}

}
