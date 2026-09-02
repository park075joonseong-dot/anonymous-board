package com.example.board.post.service;

import com.example.board.post.dto.PostCreateRequest;
import com.example.board.post.dto.PostCreateResponse;
import com.example.board.post.dto.PostDetailResponse;
import com.example.board.post.entity.Post;
import com.example.board.post.exception.PostNotFoundException;
import com.example.board.post.repository.PostRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

