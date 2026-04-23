package com.bulletin.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public record Response<T>(
        String code,
        String message,
        T data
) {

    public static <T> Response<T> ok(T data) {
        return new Response<>("0000", "Sukses", data);
    }

    public static <T> Response<T> created(T data) {
        return new Response<>("0001", "Berhasil membuat post", data);
    }

    public static <T> Response<T> badRequest(String message) {
        return new Response<>("0400", message, null);
    }

    public static <T> Response<T> notFound(String message) {
        return new Response<>("0404", message, null);
    }

    public static <T> Response<T> unauthorized(String message) {
        return new Response<>("0401", message, null);
    }

    public static <T> Response<T> forbidden(String message) {
        return new Response<>("0403", message, null);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>("0500", message, null);
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {

        if (code.equals("0000")) return HttpStatus.OK;
        if (code.equals("0001")) return HttpStatus.CREATED;

        if (code.equals("0400")) return HttpStatus.BAD_REQUEST;
        if (code.equals("0404")) return HttpStatus.NOT_FOUND;
        if (code.equals("0401")) return HttpStatus.UNAUTHORIZED;
        if (code.equals("0403")) return HttpStatus.FORBIDDEN;

        if (code.equals("0500")) return HttpStatus.INTERNAL_SERVER_ERROR;

        return HttpStatus.OK;
    }
}