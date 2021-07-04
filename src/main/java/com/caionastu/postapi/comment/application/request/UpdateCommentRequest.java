package com.caionastu.postapi.comment.application.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class UpdateCommentRequest {

    @NotNull(message = "{comment.user.notNull}")
    private UUID userId;

    @NotBlank(message = "{comment.text.notBlack}")
    @Size(min = 1, max = 150, message = "{comment.text.size}")
    private String text;
}
