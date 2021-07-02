package com.caionastu.postapi.post.domain;

import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Post {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Column
    @Getter
    private String title;

    @Column
    @Getter
    private String body;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    @Getter
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public static Post from(@NonNull User user, CreatePostRequest request) {
        return new Post(
                null,
                request.getTitle(),
                request.getBody(),
                ZonedDateTime.now(),
                user);
    }

    public boolean isOwnerUser(UUID userId) {
        return user.getId().equals(userId);
    }
}
