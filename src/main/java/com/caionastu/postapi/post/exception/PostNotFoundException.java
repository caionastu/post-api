package com.caionastu.postapi.post.exception;

import com.caionastu.postapi.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class PostNotFoundException extends NotFoundException {

    @Getter
    private final UUID id;

    public PostNotFoundException(UUID id) {
        super("post.exception.notFound", id);
        this.id = id;
    }
}
