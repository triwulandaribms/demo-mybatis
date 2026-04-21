package com.bulletin.board.model.request;


public record UpdatePostRequest(
    String title,
    String author,
    String content,
    String password
) {}