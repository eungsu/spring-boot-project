package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.headers(headers -> headers
				.frameOptions(FrameOptionsConfig::sameOrigin))
			.authorizeHttpRequests(auth -> auth
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/resources/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/home", "/signup", "/login", "/logout", "/completed").permitAll()
				.anyRequest().authenticated())
			.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/home")
				.failureUrl("/login?error=fail"))
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/home")
				.invalidateHttpSession(true))
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/login?error=unauthorized"))
			.accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/login?error=forbidden")))
			.build();
	}
}
