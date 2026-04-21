package com.bulletin.board.controller;

import com.bulletin.board.model.Response;
import com.bulletin.board.model.request.DeletePostRequest;
import com.bulletin.board.model.request.PostRequest;
import com.bulletin.board.model.request.UpdatePostRequest;
import com.bulletin.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get-all")
    public ResponseEntity<Response<Object>> getAll() {
        Response<Object> res = postService.getAllPosts();
        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<Response<Object>> detail(@PathVariable Long id) {
        Response<Object> res = postService.getDetail(id);
        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }

    @PostMapping("/add")
    public ResponseEntity<Response<Object>> create(@RequestBody PostRequest request) {
        Response<Object> res = postService.create(request);
        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<Object>> update(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request) {

        Response<Object> res = postService.update(id, request);
        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<Object>> delete(
            @PathVariable Long id,
            @RequestBody DeletePostRequest request) {

        Response<Object> res = postService.delete(id, request.password());
        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }
}