package com.tidder.service;

import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.dto.PostMessage;
import com.tidder.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    Post getOne(Long id);
    Post createdPost(MultipartFile multipartFile, User user) throws IOException;
    boolean deletePost(Post post);
    Post likedPost(Post post, User user);
    List<UserDto> getLikers(Post post);

    Post addMessageToPost(PostMessage postMessage, Post post);
}
