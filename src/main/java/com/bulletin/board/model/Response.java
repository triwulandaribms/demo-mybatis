package com.bulletin.board.model;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record Response<T>(String code, String message, T data) {

    public static <T> Response<T> create(String serviceCode, String responseCode, String message, T data) {
        return new Response<>(serviceCode + responseCode, message, data);
    }

    public static Response<Object> unauthenticated() {
        return new Response<>("0101", "Unauthenticated", null);
    }

    public static Response<Object> unauthorized() {
        return new Response<>("0201", "Unauthorized", null);
    }

    public static <T> Response<T> badRequest(String message) {
        return new Response<>("0301", message, null);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>("0000", "Sukses", data);
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {

        if (code.equals("0101")) return HttpStatus.UNAUTHORIZED;
        if (code.equals("0201")) return HttpStatus.FORBIDDEN;
        if (code.equals("0301")) return HttpStatus.BAD_REQUEST;

        if (code.endsWith("01")) return HttpStatus.CREATED;

        if (code.endsWith("04")) return HttpStatus.NOT_FOUND;

        if (code.endsWith("00")) return HttpStatus.OK;

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}