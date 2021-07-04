package com.caionastu.postapi.post.service;

import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.post.application.request.UpdatePostRequest;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.exception.UserNotPostOwnerException;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudPostService {

    private final FindUserService findUserService;
    private final FindPostService findPostService;
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

    public Post update(UUID id, UpdatePostRequest request) {
        User user = findUserService.byIdAndIsActive(request.getUserId());

        log.info("Checking if post exists.");
        Post post = findPostService.byId(id);
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
                user,
                List.of());

        repository.save(post);
        log.info("Post updated.");
        return post;
    }

    public void delete(UUID id, UUID userId) {
        findUserService.checkIfExistAndIsActive(userId);

        log.info("Checking if post exists.");
        Post post = findPostService.byId(id);
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
