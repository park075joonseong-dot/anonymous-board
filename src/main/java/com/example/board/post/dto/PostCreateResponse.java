package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostCreateResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime CreatedAt;

}
