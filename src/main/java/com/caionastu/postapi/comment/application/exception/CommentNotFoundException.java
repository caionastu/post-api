package com.caionastu.postapi.comment.application.exception;

import com.caionastu.postapi.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class CommentNotFoundException extends NotFoundException {

    @Getter
    private final UUID id;

    public CommentNotFoundException(UUID id) {
        super("comment.exception.notFound", id);
        this.id = id;
    }

}
