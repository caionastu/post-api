package com.caionastu.postapi.post.application;

import com.caionastu.postapi.commons.application.annotation.ApiPageable;
import com.caionastu.postapi.commons.application.response.ApiCollectionResponse;
import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.post.application.request.PostFilterRequest;
import com.caionastu.postapi.post.application.response.PostResponse;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.post.repository.PostSpecification;
import com.caionastu.postapi.user.service.FindUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/posts")
@AllArgsConstructor
@Slf4j
@Api(value = "post-operations", tags = "Post Operations")
public class PostController {

    private final PostRepository repository;
    private final FindUserService findUserService;

    @ApiPageable
    @GetMapping(path = "/user/{userId}")
    @ApiOperation("Search all posts by user")
    public ResponseEntity<ApiCollectionResponse<PostResponse>> findAllByUser(@ApiIgnore Pageable pageable, @PathVariable UUID userId, PostFilterRequest filter) {
        log.info("Receiving request to find all posts by user userId: {}.", userId);

        log.info("Checking if user exists.");
        findUserService.existsById(userId);

        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "date"));
        }

        log.info("Searching all posts of user by userId {}.", userId);
        Page<PostResponse> posts = repository.findAll(PostSpecification.from(userId, filter), pageable)
                .map(PostResponse::from);

        ApiCollectionResponse<PostResponse> response = ApiCollectionResponse.from(posts);
        log.info("All posts retrieved.");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody CreatePostRequest request) {
        return ResponseEntity.ok().build();
    }

}
