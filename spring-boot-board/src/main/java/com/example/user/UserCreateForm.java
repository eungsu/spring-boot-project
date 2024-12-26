package com.example.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCreateForm {

	@NotEmpty(message = "사용자 아이디는 필수항목입니다.")
	@Size(min = 3, max = 25)
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	private String email;
}
