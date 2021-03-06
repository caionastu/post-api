package com.caionastu.postapi.commons.configuration;

import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Configuration
public class DatabaseDataConfiguration {

    @Bean
    public CommandLineRunner run(UserRepository userRepository, PostRepository postRepository) {
        return (String[] args) -> {
            log.info("=== Loading Database data ===");

            User user = new User(null, "John", "john@email.com", true);
            User user2 = new User(null, "Snow", "snow@email.com", true);

            userRepository.save(user);
            userRepository.save(user2);

            log.info("{}", user);

            Post post = new Post(null, "Post Title", "Post content body", ZonedDateTime.now(), user, List.of());
            Post post2 = new Post(null, "Post Title 2", "Post content body", ZonedDateTime.now(), user, List.of());
            Post post3 = new Post(null, "Post Title 3", "Post content body", ZonedDateTime.now(), user, List.of());
            Post post4 = new Post(null, "Post Title 4", "Post content body", ZonedDateTime.now(), user, List.of());
            Post post5 = new Post(null, "Post Title 5", "Post content body", ZonedDateTime.now(), user, List.of());
            postRepository.save(post);
            postRepository.save(post2);
            postRepository.save(post3);
            postRepository.save(post4);
            postRepository.save(post5);

            log.info("{}", post);

            log.info("=== Database loaded ===");
        };
    }
}
