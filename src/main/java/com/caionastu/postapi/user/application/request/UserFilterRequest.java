package com.caionastu.postapi.user.application.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFilterRequest {

    @Getter
    private String name;

    @Getter
    private String email;

    private Boolean active;

    public boolean isActive() {
        if (Objects.isNull(active)) {
            return true;
        }

        return active;
    }
}
