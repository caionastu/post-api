package com.caionastu.postapi.comment.application;

import com.caionastu.postapi.comment.application.request.CommentFilterRequest;
import com.caionastu.postapi.comment.application.request.CreateCommentRequest;
import com.caionastu.postapi.comment.application.request.UpdateCommentRequest;
import com.caionastu.postapi.comment.application.response.CommentResponse;
import com.caionastu.postapi.comment.application.service.CrudCommentService;
import com.caionastu.postapi.comment.domain.Comment;
import com.caionastu.postapi.comment.repository.CommentRepository;
import com.caionastu.postapi.comment.repository.CommentSpecification;
import com.caionastu.postapi.commons.application.response.ApiCollectionResponse;
import com.caionastu.postapi.commons.util.PageableUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/comments")
@AllArgsConstructor
@Slf4j
@Api(value = "comment-operation", tags = "Comment Operations")
public class CommentController {

    private final CommentRepository repository;
    private final CrudCommentService service;

    @GetMapping
    @ApiOperation("Find all comments")
    public ResponseEntity<ApiCollectionResponse<CommentResponse>> findAll(@Valid CommentFilterRequest request, Pageable pageable) {
        log.info("Receiving request to search all comments. Request: {}.", pageable);

        log.info("Searching all comments.");
        pageable = PageableUtils.sort(pageable, Sort.Direction.DESC, "date");
        Page<CommentResponse> responsePage = repository.findAll(CommentSpecification.from(request), pageable)
                .map(CommentResponse::from);

        log.info("Retrieving all comments.");
        ApiCollectionResponse<CommentResponse> response = ApiCollectionResponse.from(responsePage);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Create a new comment in a post")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CreateCommentRequest request) {
        Comment comment = service.create(request);
        CommentResponse response = CommentResponse.from(comment);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Update a comment")
    public ResponseEntity<CommentResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateCommentRequest request) {
        Comment comment = service.update(id, request);
        CommentResponse response = CommentResponse.from(comment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Delete a comment")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam("userId") UUID userId) {
        service.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
