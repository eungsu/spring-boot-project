package com.example.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.reply.Reply;
import com.example.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter @Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 255, nullable = false)
	private String title;
	
	@ManyToOne
	private SiteUser author;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Reply> replies;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private Set<PostVoter> postVoters;
}
