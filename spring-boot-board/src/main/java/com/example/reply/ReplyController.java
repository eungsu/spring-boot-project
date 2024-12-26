package com.example.reply;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.post.Post;
import com.example.post.PostService;
import com.example.user.SiteUser;
import com.example.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	private final PostService postService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/create/{postId}")
	public String createReply(@PathVariable(name = "postId") Integer postId,
			Principal principal,
			@Valid ReplyForm replyForm, 
			BindingResult bindingResult, 
			Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("post", postService.getPost(postId));
			return "post/detail";
		}
		
		Post post = postService.getPost(postId);
		SiteUser siteUser = userService.getUser(principal.getName());
		Reply reply = replyService.create(post, replyForm.getContent(), siteUser);
		
		return String.format("redirect:/post/detail/%s#reply_%s", postId, reply.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/modify/{replyId}")
	public String modify(@PathVariable(name = "replyId") Integer replyId, Principal principal, Model model) {
		Reply reply = replyService.getReply(replyId);
		if (!reply.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		ReplyForm replyForm = new ReplyForm();
		replyForm.setContent(reply.getContent());
		
		model.addAttribute("replyForm", replyForm);
		
		return "reply/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/modify/{replyId}")
	public String modify(@PathVariable(name = "replyId") Integer replyId,  @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "reply/form";
		}
		Reply reply = replyService.getReply(replyId);
		if (!reply.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		replyService.modify(reply, replyForm.getContent());
		
		return String.format("redirect:/post/detail/%s#reply_%s", reply.getPost().getId() , reply.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/delete/{replyId}")
	public String delete(@PathVariable(name = "replyId") Integer replyId, Principal principal) {
		Reply reply = replyService.getReply(replyId);
		if (!reply.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		replyService.delete(reply);
		
		return String.format("redirect:/post/detail/%s", reply.getPost().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/vote/{replyId}")
	public String vote(@PathVariable(name = "replyId") Integer replyId, Principal principal) {
		Reply reply = replyService.getReply(replyId);
		SiteUser siteUser = userService.getUser(principal.getName());
		replyService.vote(reply, siteUser);
		
		return String.format("redirect:/post/detail/%s#reply_%s", reply.getPost().getId() , reply.getId());
	}
}
