package com.example.reply;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.SiteUser;

public interface ReplyVoterRepository extends JpaRepository<ReplyVoter, Integer>{

	Optional<ReplyVoter> findByReplyAndVoter(Reply reply, SiteUser voter);
}
