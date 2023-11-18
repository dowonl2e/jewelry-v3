package com.jewelry.user.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final UserEntity userEntity;

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
    	Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> userEntity.getUserRole().getRole()); // key: ROLE_권한
        return authorities;
	}

	@Override
	public String getUsername() {	
		return userEntity.getUserId();
	}
	
	@Override
	public String getPassword() {
		return userEntity.getUserPwd();
	}

	@Override
	public boolean isAccountNonExpired() {	// 계정의 만료 여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	// 계정의 잠김 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	// 비밀번호 만료 여부
		return true;
	}

	@Override
	public boolean isEnabled() {	// 계정의 활성화 여부
		return true;
	}
	
	
}