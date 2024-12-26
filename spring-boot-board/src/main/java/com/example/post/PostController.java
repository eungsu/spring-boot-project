package com.example.post;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.reply.ReplyForm;
import com.example.user.SiteUser;
import com.example.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final UserService userService;
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<Post> paging = postService.getPosts(page);
		model.addAttribute("paging", paging);
		
		return "post/list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable(name = "id") Integer id,  Model model) {
		model.addAttribute("post", postService.getPost(id));
		model.addAttribute("replyForm", new ReplyForm());
		
		return "post/detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String form(Model model) {
		model.addAttribute("postForm", new PostForm());
		
		return "post/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String create(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "post/form";
		}
		SiteUser siteUser = userService.getUser(principal.getName());
		postService.create(postForm.getTitle(), postForm.getContent(), siteUser);
		
		return "redirect:/post/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyform(@PathVariable(name = "id") Integer id, Principal principal, Model model) {
		Post post = postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		PostForm postForm = new PostForm();
		postForm.setTitle(post.getTitle());
		postForm.setContent(post.getContent());
		model.addAttribute("postForm", postForm);
		
		return "post/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(@PathVariable(name = "id") Integer id, @Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "post/form";
		}
		
		Post post = postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		postService.modify(post, postForm.getTitle(), postForm.getContent());
		
		return String.format("redirect:/post/detail/%s", id);
	}
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String modify(@PathVariable(name = "id") Integer id, Principal principal) {
		
		Post post = postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		postService.delete(post);
		
		return String.format("redirect:/post/list");
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String vote(@PathVariable(name = "id") Integer id, Principal principal) {
		Post post = postService.getPost(id);
		SiteUser siteUser = userService.getUser(principal.getName());
		postService.vote(post, siteUser);
		
		return String.format("redirect:/post/detail/%s", id);
	}
}
