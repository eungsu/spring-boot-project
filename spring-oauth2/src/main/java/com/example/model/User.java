package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	private String nickname;

	private String provider;
	
	@Enumerated(EnumType.STRING)
	private ERole role;
}
