package com.caionastu.postapi.comment.application.request;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class CommentFilterRequest {

    private UUID userId;
    private UUID postId;
    private String text;

}
