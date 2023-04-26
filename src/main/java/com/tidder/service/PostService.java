package com.tidder.service;

import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    Post getOne(Long id);
    boolean createdPost(PostDto postDto, String mail) throws IOException;
    boolean deletePost(Post post);
    boolean likedPost(Post post, User user);
    List<User> getLikers(Post post);
}
