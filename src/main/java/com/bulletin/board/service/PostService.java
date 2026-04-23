package com.bulletin.board.service;

import com.bulletin.board.entity.Post;
import com.bulletin.board.mapper.PostMapper;
import com.bulletin.board.model.Response;
import com.bulletin.board.model.request.PostRequest;
import com.bulletin.board.model.request.UpdatePostRequest;
import com.bulletin.board.model.response.CreatePostResponse;
import com.bulletin.board.model.response.DetailPostResponse;
import com.bulletin.board.model.response.ListPostResponse;
// import com.bulletin.board.model.response.UpdateDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;
    private final PasswordEncoder passwordEncoder;


    private boolean cekKarakterKorea(String text) {
        return text != null && text.matches(".*[가-힣].*");
    }

    private String validasiPost(
            String title,
            String author,
            String password,
            String content,
            boolean isUpdate) {

        if (title == null || title.isBlank()) {
            return "Title tidak boleh kosong";
        }

        if (author == null || author.isBlank()) {
            return "Author tidak boleh kosong";
        }

        if (content == null || content.isBlank()) {
            return "Content tidak boleh kosong";
        }

        if (!isUpdate) {
            if (password == null || password.isBlank()) {
                return "Password tidak boleh kosong";
            }
        
            if (password.length() < 6) {
                return "Password minimal 6 karakter";
            }
        }

        if (!author.matches("^[a-zA-Z0-9 ]+$")) {
            return "Author hanya boleh huruf dan angka";
        }
        
        if (cekKarakterKorea(title)) {
            if (title.length() > 50) {
                return "Title maksimal 50 karakter untuk bahasa Korea";
            }
        } else {
            if (title.length() > 100) {
                return "Title maksimal 100 karakter untuk bahasa Inggris";
            }
        }

        if (author.length() > 10) {
            return "Author maksimal 10 karakter";
        }

        return null;
    }
    public Response<Object> getAllPosts() {

        try {
    
            List<Post> cekData = postMapper.getAll();
    
            if (cekData.isEmpty()) {
                return Response.ok(new ArrayList<>());
            }
    
            List<ListPostResponse> dataAll = new ArrayList<>();
    
            for (Post data : cekData) {
                dataAll.add(new ListPostResponse(
                        data.getId(),
                        data.getNumberPost(),
                        data.getTitle(),
                        data.getAuthor(),
                        data.getViews(),
                        data.getCreatedAt(),
                        data.getUpdatedAt()
                ));
            }
    
            return Response.ok(dataAll);
    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }

    public Response<Object> getDetail(Long id) {

        try {
    
            Post data = postMapper.getById(id);
    
            if (data == null) {
                return Response.notFound("Post tidak ditemukan");
            }
    
            postMapper.views(id);

            Post updated = postMapper.getById(id);

            DetailPostResponse response = new DetailPostResponse(
                    updated.getNumberPost(),
                    updated.getTitle(),
                    updated.getAuthor(),
                    updated.getViews(),
                    updated.getCreatedAt(),
                    updated.getUpdatedAt(),
                    updated.getContent()
            );
    
            return Response.ok(response);

    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }

    public Response<Object> create(PostRequest request) {

        try {
    
            String validasi = validasiPost(
                    request.title(),
                    request.author(),
                    request.password(),
                    request.content(),
                    false);
    
            if (validasi != null) {
                return Response.badRequest(validasi);
            }
    
            Post data = new Post();
            data.setTitle(request.title());
            data.setAuthor(request.author());
            data.setPassword(passwordEncoder.encode(request.password()));
            data.setContent(request.content());
    
            postMapper.createPost(data);
    
            CreatePostResponse hasil = new CreatePostResponse(
                    data.getId(),
                    data.getTitle(),
                    data.getAuthor(),
                    data.getContent()
            );
    
            return Response.created(hasil);
    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }

    public Response<Object> update(Long id, UpdatePostRequest request) {

        try {
    
            Post cekData = postMapper.getById(id);
    
            if (cekData == null) {
                return Response.notFound("Post tidak ditemukan");
            }
    
            if (request.password() == null ||
                    !passwordEncoder.matches(request.password(), cekData.getPassword())) {
                return Response.badRequest("Password tidak sesuai");
            }
    
            String validasi = validasiPost(
                    request.title(),
                    request.author(),
                    request.password(),
                    request.content(),
                    true);
    
            if (validasi != null) {
                return Response.badRequest(validasi);
            }
    
            Post post = new Post();
            post.setId(id);
            post.setTitle(request.title());
            post.setAuthor(request.author());
            post.setContent(request.content());
    
            postMapper.updatePost(post);
    
            return Response.ok("Post dengan id " + id + " berhasil diupdate");
    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }

    public Response<Object> getDetailNoView(Long id) {

        try {
    
            Post data = postMapper.getByIdNoView(id);
    
            if (data == null) {
                return Response.notFound("Post tidak ditemukan");
            }
    
            DetailPostResponse response = new DetailPostResponse(
                    data.getNumberPost(),
                    data.getTitle(),
                    data.getAuthor(),
                    data.getViews(),
                    data.getCreatedAt(),
                    data.getUpdatedAt(),
                    data.getContent()
            );
    
            return Response.ok(response);
    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }
    public Response<Object> delete(Long id, String password) {

        try {
    
            Post cekData = postMapper.getById(id);
    
            if (cekData == null) {
                return Response.notFound("Post tidak ditemukan");
            }
    
            if (password == null ||
                    !passwordEncoder.matches(password, cekData.getPassword())) {
                return Response.badRequest("Password salah");
            }
    
            postMapper.deletePost(id);
    
            return Response.ok("Post dengan id " + id + " berhasil dihapus");
    
        } catch (Exception e) {
            return Response.error("Internal server error");
        }
    }
}