package com.caionastu.postapi.comment.domain;

import com.caionastu.postapi.comment.application.request.CreateCommentRequest;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @Setter
    private String text;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Post post;

    public static Comment from(CreateCommentRequest request, User user, Post post) {
        return new Comment(
                null,
                request.getText(),
                ZonedDateTime.now(),
                user,
                post
        );
    }

    public boolean isOwnerUser(UUID userId) {
        return user.getId().equals(userId);
    }
}
