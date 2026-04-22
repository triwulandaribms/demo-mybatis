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

        if (!isUpdate && (password == null || password.isBlank())) {
            return "Password tidak boleh kosong";
        }

        if (!isUpdate && (password == null || password.length() < 6)){
            return "Password minimal 6 karakter";
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
                return Response.create("07", "00", "Data post masih kosong", new ArrayList<>());
            }

            List<ListPostResponse> dataAll = new ArrayList<>();

            for (Post data : cekData) {
                dataAll.add(new ListPostResponse(
                        data.getNumberPost(),
                        data.getTitle(),
                        data.getAuthor(),
                        data.getViews(),
                        data.getCreatedAt()));
            }

            return Response.create("07", "00", "Sukses", dataAll);

        } catch (Exception e) {
            return Response.create("07", "99", "Internal server error", null);
        }
    }

    public Response<Object> getDetail(Long id) {

        try {

            Post data = postMapper.getById(id);

            if (data == null) {
                return Response.create("07", "04", "Post tidak ditemukan", null);
            }

            postMapper.views(id);

            data.setViews(data.getViews() + 1);

            DetailPostResponse response = new DetailPostResponse(
                    data.getNumberPost(),
                    data.getTitle(),
                    data.getAuthor(),
                    data.getViews(),
                    data.getCreatedAt(),
                    data.getUpdatedAt(),
                    data.getContent());

            return Response.create("07", "00", "Sukses", response);

        } catch (Exception e) {
            return Response.create("07", "99", "Internal server error", null);
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
                    data.getContent());

            return Response.create("07", "01", "Post berhasil dibuat", hasil);

        } catch (Exception e) {
            return Response.create("07", "99", "Internal server error", null);
        }
    }

    public Response<Object> update(Long id, UpdatePostRequest request) {

        try {

            Post cekData = postMapper.getById(id);

            if (cekData == null) {
                return Response.create("07", "04", "Post tidak ditemukan", null);
            }

            if (request.password() == null ||
                    !passwordEncoder.matches(request.password(), cekData.getPassword())) {
                return Response.create("07", "02", "Password tidak sesuai", null);
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

            return Response.create(
                    "07",
                    "00",
                    "Post dengan id " + id + " berhasil diupdate",
                    null
            );

        } catch (Exception e) {
            return Response.create("07", "99", "Internal server error", null);
        }
    }

    public Response<Object> delete(Long id, String password) {

        try {

            Post cekData = postMapper.getById(id);

            if (cekData == null) {
                return Response.create("07", "04", "Post tidak ditemukan", null);
            }

            if (password == null ||
                    !passwordEncoder.matches(password, cekData.getPassword())) {
                return Response.create("07", "02", "Password salah", null);
            }

            postMapper.deletePost(id);

            return Response.create(
                    "07",
                    "00",
                    "Post dengan id " + id + " berhasil dihapus",
                    null
            );
        } catch (Exception e) {
            return Response.create("07", "99", "Internal server error", null);
        }
    }


}