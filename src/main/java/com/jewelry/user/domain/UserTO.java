package com.jewelry.user.domain;

import com.jewelry.common.domain.CommonTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTO extends CommonTO {

	private String user_pwd;
	private String user_name;
	private String user_role;
	private String email;
	private String celnum;
	private String gender;
	private String use_yn;
	private String inpt_id;
	private String inpt_dt;
	private String updt_id;
	private String updt_dt;

}
