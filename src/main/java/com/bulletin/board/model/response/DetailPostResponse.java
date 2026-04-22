package com.bulletin.board.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DetailPostResponse(
    Integer numberPost,
    String title,
    String author,
    int views,
    LocalDateTime createdAt,
    LocalDateTime updatedAt, 
    String content
) {}