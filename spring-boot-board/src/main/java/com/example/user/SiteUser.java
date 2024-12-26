package com.example.user;

import java.util.List;

import com.example.post.PostVoter;
import com.example.reply.ReplyVoter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter @Setter
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@OneToMany(mappedBy = "voter")
	private List<PostVoter> postVoters;

	@OneToMany(mappedBy = "voter")
	private List<ReplyVoter> replyVoters;
}
