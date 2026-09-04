package com.example.board.post.service;

import com.example.board.post.dto.*;
import com.example.board.post.entity.Post;
import com.example.board.post.exception.PostNotFoundException;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public PostCreateResponse create(PostCreateRequest request) {
        String passwordHash = passwordEncoder.encode(request.getPassword());
        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                passwordHash
        );
        Post savedPost = postRepository.save(post);

        return new PostCreateResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException());
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
    @Transactional(readOnly = true)
    public PostListResponse getList(int page){
        Pageable pageable = PageRequest.of(
                page,
                15,
                Sort.by(Sort.Direction.DESC,"createdAt")
        );
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostSummaryResponse> posts = postPage.getContent()
                .stream()
                .map(post -> new PostSummaryResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getCreatedAt()
                ))
                .toList();
        return new PostListResponse(
                posts,
                postPage.getNumber(),
                postPage.getTotalPages()
        );
    }
}

