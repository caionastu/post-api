package com.caionastu.postapi.post.application.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdatePostRequest {

    @NotNull(message = "{post.user.notNull}")
    private UUID userId;

    @NotBlank(message = "{post.title.notBlank}")
    @Size(min = 4, max = 30, message = "{post.title.size}")
    private String title;

    @NotBlank(message = "{post.body.notBlank}")
    @Size(min = 1, max = 150, message = "{post.body.size}")
    private String body;

}
