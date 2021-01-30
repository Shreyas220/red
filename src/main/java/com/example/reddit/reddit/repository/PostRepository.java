package com.example.reddit.reddit.repository;

import com.example.reddit.reddit.models.Post;
import com.example.reddit.reddit.models.Subreddit;
import com.example.reddit.reddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}