package com.example.reply;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.error.DataNotFoundException;
import com.example.post.Post;
import com.example.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final ReplyVoterRepository replyVoterRepository;
	
	public Reply create(Post post, String content, SiteUser siteUser) {
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setCreatedDate(LocalDateTime.now());
		reply.setPost(post);
		reply.setAuthor(siteUser);
		
		return replyRepository.save(reply);
	}

	public Reply getReply(Integer replyId) {
		Optional<Reply> optionalReply = replyRepository.findById(replyId);
		if (optionalReply.isEmpty()) {
			throw new DataNotFoundException("댓글정보가 존재하지 않습니다.");
		}
		
		return optionalReply.get();
	}

	public void modify(Reply reply, String content) {
		reply.setContent(content);
		reply.setUpdatedDate(LocalDateTime.now());
		
		replyRepository.save(reply);
	}
	
	public void delete(Reply reply) {
		replyRepository.delete(reply);
	}
	
	public void vote(Reply reply, SiteUser siteUser) {
		ReplyVoter replyVoter = new ReplyVoter();
		replyVoter.setReply(reply);
		replyVoter.setVoter(siteUser);
		
		Optional<ReplyVoter> optionReplyVoter = replyVoterRepository.findByReplyAndVoter(reply, siteUser);
		if (optionReplyVoter.isEmpty()) {
			replyVoterRepository.save(replyVoter);
		} else {
			replyVoterRepository.delete(optionReplyVoter.get());
		}
	}
}
