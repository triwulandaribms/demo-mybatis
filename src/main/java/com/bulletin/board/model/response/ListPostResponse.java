package com.bulletin.board.model.response;

import java.time.LocalDateTime;

public record ListPostResponse(
    Integer numberPost,
    String title,
    String author,
    int views,
    LocalDateTime createdAt
) {}