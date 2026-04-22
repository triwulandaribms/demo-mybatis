package com.bulletin.board.model.response;

public record UpdateDeleteResponse(
    Long id,
    Integer numberPost,
    String title,
    String author,
    String content
) {
    
}

