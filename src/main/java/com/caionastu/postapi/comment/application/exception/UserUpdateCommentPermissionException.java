package com.caionastu.postapi.comment.application.exception;

import com.caionastu.postapi.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class UserUpdateCommentPermissionException extends NotFoundException {

    @Getter
    private final UUID id;

    public UserUpdateCommentPermissionException(UUID id) {
        super("comment.exception.delete.permission", id);
        this.id = id;
    }

}
