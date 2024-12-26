package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.payload.user.SignupUserRequest;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final UserService userService;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/signup")
	public String signupForm() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(SignupUserRequest request) {
		userService.createUser(request);
		
		return "redirect:/completed";
	}
	
	@GetMapping("/completed")
	public String completed() {
		return "completed";
	}
	
	@GetMapping("/login")
	public String loginform() {
		return "loginform";
	}
	
}
