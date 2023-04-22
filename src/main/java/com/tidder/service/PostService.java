package com.tidder.service;

import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.dto.PostDto;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    Post getOne(Long id);
    boolean createdPost(PostDto postDto);
    boolean deletePost(Post post);
    boolean updatePost(Post post, PostDto updatedPost);
    boolean likedPost(Post post, User user);
    List<User> getLikers(Post post);
}
