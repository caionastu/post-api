package com.caionastu.postapi.post.service;

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
public class DeletePostService {

    private final FindUserService findUserService;
    private final PostRepository repository;

    public void delete(UUID id, UUID userId) {
        log.info("Checking if user exists and is active.");
        User user = findUserService.byId(userId);
        if (!user.isActive()) {
            log.error("User with id {} is deactivated.", userId);
            throw new UserDeactivatedException(userId);
        }

        log.info("Checking if post exists.");
        Post post = repository.findById(id).orElseThrow(() -> {
            log.error("Post not found with id: {}.", id);
            throw new PostNotFoundException(id);
        });

        log.info("Checking if user is owner of the post.");
        if (!post.isOwnerUser(userId)) {
            log.error("User with id {} is not post owner.", userId);
            throw new UserNotPostOwnerException(userId);
        }

        log.info("Deleting post.");
        repository.deleteById(id);
        log.info("Post and all of its comments deleted.");
    }
}
