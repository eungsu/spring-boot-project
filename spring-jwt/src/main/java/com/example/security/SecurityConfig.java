package com.example.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.payload.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.headers(headers -> headers
				.frameOptions(FrameOptionsConfig::sameOrigin))
			.cors(cors -> cors
				.configurationSource(corsConfigurationSource()))
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/api/auth/*").permitAll()
				.requestMatchers("/api/user/signup").permitAll()
				.anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((req, res, authException) -> {
					res.setContentType("application/json; charset=utf-8");
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

					Response<Void> response = Response.fail(HttpServletResponse.SC_UNAUTHORIZED, "유효한 JWT 토큰이 아닙니다.");
					ObjectMapper objectMapper = new ObjectMapper();
					res.getWriter().write(objectMapper.writeValueAsString(response));
				})
				.accessDeniedHandler((req,  res, accessDeniedException) -> {
					res.setContentType("application/json; charset=utf-8");
					res.setStatus(HttpServletResponse.SC_FORBIDDEN);

					Response<Void> response = Response.fail(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
					ObjectMapper objectMapper = new ObjectMapper();
					res.getWriter().write(objectMapper.writeValueAsString(response));
				}))
			.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(List.of("X-Requested-With", "Content-Type", "Authorization", "X-XSRF-toke"));
		configuration.setAllowCredentials(false);
		configuration.setMaxAge(3600L);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
