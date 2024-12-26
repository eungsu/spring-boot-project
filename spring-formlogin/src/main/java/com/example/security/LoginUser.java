package com.example.security;

import lombok.Getter;

@Getter
public class LoginUser {

	private Long id;
	private String nickname;
	
	public LoginUser(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
}
