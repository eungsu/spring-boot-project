package com.example.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.error.DataNotFoundException;
import com.example.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostVoterRepository postVoterRepository;
	
	public Page<Post> getPosts(int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by(List.of(Sort.Order.desc("createdDate"))));
		return postRepository.findAll(pageable);
	}
	
	public Post getPost(Integer id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isEmpty()) {
			throw new DataNotFoundException("게시글정보가 존재하지 않습니다.");
		}
		
		return optionalPost.get();
	}
	
	public void create(String title, String content, SiteUser siteUser) {
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setCreatedDate(LocalDateTime.now());
		post.setAuthor(siteUser);
		
		postRepository.save(post);
	}
	
	public void modify(Post post, String title, String content) {
		post.setTitle(title);
		post.setContent(content);
		post.setUpdatedDate(LocalDateTime.now());
		
		postRepository.save(post);
	}

	public void delete(Post post) {
		postRepository.delete(post);
	}
	
	public void vote(Post post, SiteUser siteUser) {
		PostVoter postVoter = new PostVoter();
		postVoter.setPost(post);
		postVoter.setVoter(siteUser);
		
		Optional<PostVoter> optionPostVoter = postVoterRepository.findByPostAndVoter(post, siteUser);
		if (optionPostVoter.isEmpty()) {
			postVoterRepository.save(postVoter);
		} else {
			postVoterRepository.delete(optionPostVoter.get());
		}
	}
}
