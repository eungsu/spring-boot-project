package com.example.security.formlogin;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.model.User;
import com.example.security.LoginUser;

// 일반 로그인처리에 필요한 사용자 정보를 표현하는 클래스
public class CustomUserDetails extends LoginUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	// 회원아이디
	private String username;
	// 회원비밀번호
	private String password;
	// 회원의 접근권한
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails(User user) {
		super(user.getId(), user.getNickname());
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
