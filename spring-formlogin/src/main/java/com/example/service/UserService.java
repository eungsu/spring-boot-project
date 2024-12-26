package com.example.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.ERole;
import com.example.model.User;
import com.example.payload.user.SignupUserRequest;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	// 신규 회원 가입 기능
	public void createUser(SignupUserRequest request) {
		// 사용자이름(유저아이디) 중복 체크하기
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("사용할 수 없는 사용자이름입니다.");
		}
		// 이메일 중복 체크하기
		if (userRepository.existsByEmail(request.getEmail())) {			
			throw new IllegalArgumentException("사용할 수 없는 이메일입니다.");
		}

		// SignupUserRequest객체의 요청정보를 User객체에 복사한다.
		User user = modelMapper.map(request, User.class);
		// 비밀번호를 암호화해서 User객체에 저장한다.
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		// 기본적으로 신규 사용자의 접근 권한은 ROLE_USER 권한이다.
		user.setRole(ERole.ROLE_USER);
		
		// 신규 사용자정보를 데이터베이스에 저장시킨다.
		userRepository.save(user);
	}
}
