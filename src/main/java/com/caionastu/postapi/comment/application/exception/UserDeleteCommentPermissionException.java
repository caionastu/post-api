package com.caionastu.postapi.comment.application.exception;

import com.caionastu.postapi.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class UserDeleteCommentPermissionException extends NotFoundException {

    @Getter
    private final UUID id;

    public UserDeleteCommentPermissionException(UUID id) {
        super("comment.exception.permission", id);
        this.id = id;
    }

}
