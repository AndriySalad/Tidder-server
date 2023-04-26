package com.tidder.controllers;

import com.tidder.domains.Post;
import com.tidder.dto.PostDto;
import com.tidder.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAll(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Long id){
        return postService.getOne(id);
    }

    @PostMapping("/add")
    public HttpResponse<HttpStatus> addPost(@RequestBody PostDto postDto, Principal principal) throws IOException {

        String mail = principal.getName();
        postService.createdPost(postDto, mail);

        return null;
    }
}
