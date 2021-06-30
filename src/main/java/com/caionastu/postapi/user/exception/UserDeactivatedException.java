package com.caionastu.postapi.user.exception;

import com.caionastu.postapi.commons.exception.BusinessException;
import lombok.Getter;

import java.util.UUID;

public class UserDeactivatedException extends BusinessException {

    @Getter
    private final UUID id;

    public UserDeactivatedException(UUID id) {
        super("user.exception.deactivated", id);
        this.id = id;
    }
}
