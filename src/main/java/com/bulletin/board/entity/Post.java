package com.bulletin.board.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Post {
    private Integer numberPost;
    private Long id;
    private String title;
    private String author;
    private String password;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}