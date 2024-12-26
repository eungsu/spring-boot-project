package com.example.reply;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.post.Post;
import com.example.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "replies")
@Getter @Setter
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private SiteUser author;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE)
	private Set<ReplyVoter> replyVoters;

	
}
