package com.caionastu.postapi.post.application.request;

import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreatePostRequest {

    private UUID userId;
    private String title;
    private String body;

    public Post toEntity(UserRepository userRepository) {
        User user = userRepository.getById(userId);
        return new Post(null, title, body, ZonedDateTime.now(), user);
    }

}
