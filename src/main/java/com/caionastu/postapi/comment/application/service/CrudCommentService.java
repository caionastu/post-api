package com.caionastu.postapi.comment.application.service;

import com.caionastu.postapi.comment.application.exception.UserDeleteCommentPermissionException;
import com.caionastu.postapi.comment.application.exception.UserUpdateCommentPermissionException;
import com.caionastu.postapi.comment.application.request.CreateCommentRequest;
import com.caionastu.postapi.comment.application.request.UpdateCommentRequest;
import com.caionastu.postapi.comment.domain.Comment;
import com.caionastu.postapi.comment.repository.CommentRepository;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.service.FindPostService;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.service.FindUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudCommentService {

    private final FindUserService findUserService;
    private final FindPostService findPostService;
    private final FindCommentService findCommentService;
    private final CommentRepository repository;

    public Comment create(CreateCommentRequest request) {
        log.info("Receiving request to create a new comment. Request: {}.", request);
        User user = findUserService.byId(request.getUserId());

        log.info("Checking if post exists.");
        Post post = findPostService.byId(request.getPostId());

        log.info("Creating new comment.");
        Comment comment = Comment.from(request, user, post);
        repository.save(comment);
        log.info("Comment created with id: {}.", comment.getId());
        return comment;
    }

    public Comment update(UUID id, UpdateCommentRequest request) {
        log.info("Receiving request to update a comment with id {}, by user id {}.", id, request.getUserId());

        findUserService.byIdAndIsActive(request.getUserId());
        Comment comment = findCommentService.byId(id);

        log.info("Checking if user has permission to update this comment.");
        if (!comment.isOwnerUser(request.getUserId())) {
            log.error("User with id {} isn't comment owner.", request.getUserId());
            throw new UserUpdateCommentPermissionException(request.getUserId());
        }

        log.info("Updating comment.");
        comment.setText(request.getText());
        log.info("Comment updated.");

        return comment;
    }

    public void delete(UUID id, UUID userId) {
        log.info("Receiving request to delete a comment with id {}, by user id {}.", id, userId);
        findUserService.byIdAndIsActive(userId);
        Comment comment = findCommentService.byId(id);

        log.info("Checking if user has permission to delete this comment.");
        Post post = comment.getPost();
        if (!post.isOwnerUser(userId) && !comment.isOwnerUser(userId)) {
            log.error("User with id {} doesn't have permission to delete this comment.", userId);
            throw new UserDeleteCommentPermissionException(userId);
        }

        log.info("Deleting comment.");
        repository.deleteById(comment.getId());
        log.info("Comment deleted.");
    }
}
