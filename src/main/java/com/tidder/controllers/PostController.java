package com.tidder.controllers;

import com.tidder.domains.Comment;
import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.dto.PostMessage;
import com.tidder.dto.UserDto;
import com.tidder.service.CommentService;
import com.tidder.service.PostService;
import com.tidder.service.UserService;
import com.tidder.utils.CommentCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping
    public List<Post> getAll(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Long id){
        return postService.getOne(id);
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody MultipartFile multipartFile) throws IOException {

        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return postService.createdPost(multipartFile, user);
    }

    @PutMapping("/add/{id}")
    public Post addMessageToPost(@RequestBody PostMessage postMessage, @PathVariable Long id){

        Post post = postService.getOne(id);
        return postService.addMessageToPost(postMessage, post);

    }

    @PutMapping("/{id}/like")
    public Post likePost(@PathVariable Long id){
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = postService.getOne(id);
        return postService.likedPost(post, user);
    }

    @PostMapping("/{id}/comment/add")
    public Post commentPost(@PathVariable Long id, @RequestBody CommentCreateRequest commentCreateRequest){
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = postService.getOne(id);
        return commentService.createComment(commentCreateRequest, post, user);
    }

    @GetMapping("/{id}/comment")
    public List<Comment> getComments(@PathVariable Long id){
        Post post = postService.getOne(id);
        return commentService.getCommentsOfPost(post);
    }

    @GetMapping("/{id}/like")
    public List<UserDto> getUserLikedPOst(@PathVariable Long id){
        Post post = postService.getOne(id);
        return postService.getLikers(post);
    }
}
