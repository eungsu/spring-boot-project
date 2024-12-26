package com.example.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.error.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	public SiteUser create(UserCreateForm userCreateForm) {
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(userCreateForm.getUsername());
		siteUser.setEmail(userCreateForm.getEmail());
		siteUser.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
		siteUser.setUserRole(UserRole.ROLE_USER);
		
		return userRepository.save(siteUser);
	}

	public SiteUser getUser(String username) {
		Optional<SiteUser> optionalSiteUser = userRepository.findByUsername(username);
		if (optionalSiteUser.isEmpty()) {
			throw new DataNotFoundException("사용자 정보가 없습니다.");
		}
		
		return optionalSiteUser.get();
	}
}
