package com.caionastu.postapi.post.service;

import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePostService {

    private final FindUserService findUserService;
    private final PostRepository repository;

    public Post create(CreatePostRequest request) {
        log.info("Finding user by id: {}", request.getUserId());
        User user = findUserService.byId(request.getUserId());

        log.info("Creating new post.");
        Post newPost = Post.from(user, request);
        repository.save(newPost);
        log.info("New post created with id {}.", newPost.getId());
        return newPost;
    }
}
