package com.example.reddit.reddit.repository;

import com.example.reddit.reddit.models.Comment;
import com.example.reddit.reddit.models.Post;
import com.example.reddit.reddit.models.Vote;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
