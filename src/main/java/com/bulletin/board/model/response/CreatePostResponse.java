package com.bulletin.board.model.response;

public record CreatePostResponse(
        Long id,
        String title,
        String author,
        String content
) {}