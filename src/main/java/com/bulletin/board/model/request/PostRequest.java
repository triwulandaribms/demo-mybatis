package com.bulletin.board.model.request;


public record PostRequest(
    
    String title,
    String author,
    String password,
    String content

) {}