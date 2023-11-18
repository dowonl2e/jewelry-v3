package com.jewelry.authentication.jwt.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	
	ADMIN("ROLE_ADMIN", "관리자"),
	MANAGER("ROLE_MANAGER", "일반관리자");
	
	private final String role;
	private final String roleName;
	
}
