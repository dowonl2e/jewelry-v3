package com.jewelry.user.domain;

import com.jewelry.common.domain.CommonVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserVO extends CommonVO {

	private List<UserVO> list;
	
	private String userid;
	private String userpwd;
	private String username;
	private String email;
	private String celnum;
	private String gender;
	private String userrole;
	private String useyn;
	private String inptid;
	private String inptdt;
	private String updtid;
	private String updtdt;
}
