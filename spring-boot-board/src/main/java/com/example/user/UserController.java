package com.example.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("userCreateForm", new UserCreateForm());
		return "user/signup-form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", null, "패스워드가 일치하지 않습니다.");
			return "user/signup-form";
		}

		if (bindingResult.hasErrors()) {
			return "user/signup-form";
		}
		
		try {
			userService.create(userCreateForm);
		} catch (DataIntegrityViolationException e) {
			bindingResult.reject(null, "이미 등록된 사용자입니다.");
			return "user/signup-form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/user/login-form";
	}
}
