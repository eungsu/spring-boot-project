package com.example.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.model.User;

public class CustomUserDetails extends LoginUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
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
