package com.example.security;

import lombok.Getter;

@Getter
public class LoginUser {

	// 회원번호
	private Long id;
	// 닉네임
	private String nickname;
	
	public LoginUser(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
}
