package com.example.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.SiteUser;


public interface PostVoterRepository extends JpaRepository<PostVoter, Integer> {

	Optional<PostVoter> findByPostAndVoter(Post post, SiteUser voter);
}
