package com.caionastu.postapi.post.service;

import com.caionastu.postapi.post.application.request.UpdatePostRequest;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.exception.PostNotFoundException;
import com.caionastu.postapi.post.exception.UserNotPostOwnerException;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.exception.UserDeactivatedException;
import com.caionastu.postapi.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePostService {

    private final FindUserService findUserService;
    private final PostRepository repository;

    public Post update(UUID id, UpdatePostRequest request) {
        log.info("Checking if user exists and is active.");
        User user = findUserService.byId(request.getUserId());
        if (!user.isActive()) {
            log.error("User with id {} is deactivated.", request.getUserId());
            throw new UserDeactivatedException(id);
        }

        log.info("Checking if post exists.");
        Post post = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not find with id: {}.", id);
                    throw new PostNotFoundException(id);
                });

        log.info("Checking if user is post owner.");
        if (!post.isOwnerUser(request.getUserId())) {
            log.error("User with id {} is not post owner.", request.getUserId());
            throw new UserNotPostOwnerException(request.getUserId());
        }

        log.info("Updating post.");
        post = new Post(
                post.getId(),
                request.getTitle(),
                request.getBody(),
                post.getDate(),
                user);

        repository.save(post);
        log.info("Post updated.");
        return post;
    }
}
