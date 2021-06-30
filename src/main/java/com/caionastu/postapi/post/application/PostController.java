package com.caionastu.postapi.post.application;

import com.caionastu.postapi.commons.application.annotation.ApiPageable;
import com.caionastu.postapi.commons.application.response.ApiCollectionResponse;
import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.post.application.response.PostResponse;
import com.caionastu.postapi.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostRepository repository;

    @ApiPageable
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<ApiCollectionResponse<PostResponse>> findAllByUser(@ApiIgnore Pageable pageable, @PathVariable UUID id) {
        Page<PostResponse> posts = repository.findByUserId(id, pageable)
                .map(PostResponse::from);

        ApiCollectionResponse<PostResponse> response = ApiCollectionResponse.from(posts);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody CreatePostRequest request) {
        return ResponseEntity.ok().build();
    }

}
