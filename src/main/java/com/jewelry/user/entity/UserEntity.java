package com.jewelry.user.entity;

import com.jewelry.authentication.jwt.values.Role;
import com.jewelry.cms.code.entity.Code;
import com.jewelry.cms.menu.entity.MenuAuth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
//@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="tb_user")
public class UserEntity {
	
	@Id
	@Column(updatable = false, unique = true, nullable = false)
	private String userId;
	
	private String userPwd;
	
	private String userName;
	
	private String email;
	
	private String celnum;
	
	private String gender;
	
	private String useYn;
	private String inptId;
	private LocalDateTime inptDt = LocalDateTime.now();

	private String updtId;
	private LocalDateTime updtDt;

	@OneToMany(mappedBy = "userEntity")
	private List<Code> codes = new ArrayList<>();

	//@OneToMany(mappedBy = "userId")	//조인 대상의 변수명
	@Column(name = "user_role")
	@Enumerated(EnumType.STRING)
	private Role userRole;

	@Builder
	public UserEntity(
			String userId, String userPwd, String userName
			, String email, String celnum, String gender
			, String useYn, Role userRole, String inptId){
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.email = email;
		this.celnum = celnum;
		this.gender = gender;
		this.useYn = useYn;
		this.userRole = userRole;
		this.inptId = inptId;
	}
}
