package com.example.board.post.controller;

import com.example.board.post.dto.PostCreateRequest;
import com.example.board.post.dto.PostCreateResponse;
import com.example.board.post.dto.PostDetailResponse;
import com.example.board.post.dto.PostListResponse;
import com.example.board.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponse> create(
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostCreateResponse response = postService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getDetail(
            @PathVariable Long postId
    ) {
        PostDetailResponse response = postService.getDetail(postId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostListResponse>getList(
            @RequestParam(defaultValue = "0")  @Min(0) int page
    ) {
        PostListResponse response = postService.getList(page);

        return ResponseEntity.ok(response);
    }
}