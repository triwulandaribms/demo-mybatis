
package com.bulletin.board.model.response;

import java.time.LocalDateTime;

public record DetailPostResponse(
    Integer numberPost,
    String title,
    String author,
    int views,
    LocalDateTime createdAt,
    LocalDateTime updatedAt, 
    String content
) {}