package com.caionastu.postapi.post.service;

import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.exception.PostNotFoundException;
import com.caionastu.postapi.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindPostService {

    private final PostRepository repository;

    public Post byId(UUID id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("Post not found with id: {}.", id);
            throw new PostNotFoundException(id);
        });
    }

}
