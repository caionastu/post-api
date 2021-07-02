package com.caionastu.postapi.post.exception;

import com.caionastu.postapi.commons.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

public class UserNotPostOwnerException extends BusinessException {

    @Getter
    private final UUID userId;

    public UserNotPostOwnerException(UUID userId) {
        super("post.exception.notOwner", userId);
        this.userId = userId;
    }
}
