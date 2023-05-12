package com.tidder.service;

import com.tidder.domains.Comment;
import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.utils.CommentCreateRequest;

import java.util.List;

public interface CommentService {

    Post createComment(CommentCreateRequest commentCreateRequest, Post post, User user);
    List<Comment> getCommentsOfPost(Post post);
}
