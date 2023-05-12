package com.tidder.service;

import com.tidder.dao.CommentRepository;
import com.tidder.dao.PostRepository;
import com.tidder.dao.UserRepository;
import com.tidder.domains.Comment;
import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.utils.CommentCreateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Post createComment(CommentCreateRequest commentCreateRequest, Post post, User user) {

        Comment comment = Comment.builder()
                .messageText(commentCreateRequest.getMessageText())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);

        List<Comment> commentList = user.getListComment();
        commentList.add(comment);
        user.setListComment(commentList);
        userRepository.save(user);

        List<Comment> postCommentList = post.getListComments();
        postCommentList.add(comment);
        post.setListComments(postCommentList);
        post.setCountComments(post.getCountComments() + 1);
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Comment> getCommentsOfPost(Post post) {
        return post.getListComments();
    }
}
