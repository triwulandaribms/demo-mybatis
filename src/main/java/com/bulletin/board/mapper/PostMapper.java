package com.bulletin.board.mapper;

import com.bulletin.board.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostMapper {

    List<Post> getAll();

    Post getById(Long id);

    void createPost(Post post);

    void updatePost(Post post);

    void deletePost(Long id);

    void views(Long id);
}