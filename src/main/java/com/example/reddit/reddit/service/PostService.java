package com.example.reddit.reddit.service;


import com.example.reddit.reddit.dto.PostRequest;
import com.example.reddit.reddit.models.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    public void save (PostRequest postRequest){

    }
}
