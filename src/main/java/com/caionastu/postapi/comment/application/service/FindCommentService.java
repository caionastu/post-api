package com.caionastu.postapi.comment.application.service;

import com.caionastu.postapi.comment.application.exception.CommentNotFoundException;
import com.caionastu.postapi.comment.domain.Comment;
import com.caionastu.postapi.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindCommentService {

    private final CommentRepository repository;

    public Comment byId(UUID id) {
        log.info("Checking if comment exists.");

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Comment not found with id: {}.", id);
                    return new CommentNotFoundException(id);
                });
    }
}
