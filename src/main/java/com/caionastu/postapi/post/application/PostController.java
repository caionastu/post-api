package com.caionastu.postapi.post.application;

import com.caionastu.postapi.commons.application.annotation.ApiPageable;
import com.caionastu.postapi.commons.application.response.ApiCollectionResponse;
import com.caionastu.postapi.commons.util.PageableUtils;
import com.caionastu.postapi.post.application.request.CreatePostRequest;
import com.caionastu.postapi.post.application.request.PostFilterRequest;
import com.caionastu.postapi.post.application.request.UpdatePostRequest;
import com.caionastu.postapi.post.application.response.PostResponse;
import com.caionastu.postapi.post.domain.Post;
import com.caionastu.postapi.post.repository.PostRepository;
import com.caionastu.postapi.post.repository.PostSpecification;
import com.caionastu.postapi.post.service.CreatePostService;
import com.caionastu.postapi.post.service.DeletePostService;
import com.caionastu.postapi.post.service.UpdatePostService;
import com.caionastu.postapi.user.domain.User;
import com.caionastu.postapi.user.service.FindUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/posts")
@AllArgsConstructor
@Slf4j
@Api(value = "post-operations", tags = "Post Operations")
public class PostController {

    private final PostRepository repository;
    private final FindUserService findUserService;
    private final CreatePostService createService;
    private final UpdatePostService updateService;
    private final DeletePostService deleteService;

    @ApiPageable
    @GetMapping(path = "/user/{userId}")
    @ApiOperation("Search all posts by user")
    public ResponseEntity<ApiCollectionResponse<PostResponse>> findAllByUser(@ApiIgnore Pageable pageable, @PathVariable UUID userId, PostFilterRequest filter) {
        log.info("Receiving request to find all posts by user userId: {}.", userId);
        log.info("Checking if user exists and is active.");
        User user = findUserService.byId(userId);
        if (!user.isActive()) {
            log.info("User is not active. Returning empty.");
            return ResponseEntity.noContent().build();
        }

        log.info("Searching all posts of user by userId {}.", userId);
        pageable = PageableUtils.sort(pageable, Sort.Direction.DESC, "date");
        Page<PostResponse> posts = repository.findAll(PostSpecification.from(userId, filter), pageable)
                .map(PostResponse::from);

        ApiCollectionResponse<PostResponse> response = ApiCollectionResponse.from(posts);
        log.info("All posts retrieved.");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Create a new post")
    public ResponseEntity<PostResponse> create(@RequestBody @Valid CreatePostRequest request) {
        log.info("Receiving request to create a new post. Request: {}.", request);
        Post newPost = createService.create(request);
        PostResponse response = PostResponse.from(newPost);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Update title and body of the post")
    public ResponseEntity<PostResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdatePostRequest request) {
        log.info("Receiving request to update a post with id: {}. Request: {}.", id, request);
        Post post = updateService.update(id, request);
        PostResponse response = PostResponse.from(post);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam(name = "userId") UUID userId) {
        log.info("Receiving request to delete post with id: {}.", id);
        deleteService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
