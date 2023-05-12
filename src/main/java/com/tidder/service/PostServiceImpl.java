package com.tidder.service;

import com.tidder.dao.PostRepository;
import com.tidder.dao.UserRepository;
import com.tidder.domains.Post;
import com.tidder.domains.PostStatus;
import com.tidder.domains.User;
import com.tidder.dto.PostMessage;
import com.tidder.dto.UserDto;
import com.tidder.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileUpload fileUpload;
    private final UserMapper userMapper;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getOne(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Post createdPost(MultipartFile multipartFile, User user) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);

        Post post = Post.builder()
                .attachmentPath(imageURL)
                .postStatus(PostStatus.AVAILABLE)
                .countComments(0L)
                .messageText("")
                .countLikes(0L)
                .user(user)
                .build();

        List<Post> postList = user.getPostList();
        postList.add(post);
        user.setPostList(postList);
        user.setPostsCount(user.getPostsCount() + 1);
        userRepository.save(user);
        postRepository.save(post);

        return post;
    }

    @Override
    @Transactional
    public boolean deletePost(Post post) {
        postRepository.delete(post);
        return true;
    }

    @Override
    @Transactional
    public Post likedPost(Post post, User user) {
        if (post.getLikers().stream().filter(user1 -> user1.equals(user)).findFirst().isEmpty()){
            post.setCountLikes(post.getCountLikes() + 1);
            List<User> userList = post.getLikers();
            userList.add(user);
            post.setLikers(userList);
            postRepository.save(post);
            List<Post> postList = user.getLikedPosts();
            postList.add(post);
            user.setLikedPosts(postList);
            userRepository.save(user);
        }
        else{
            post.setCountLikes(post.getCountLikes() - 1);
            List<User> userList = post.getLikers();
            userList.remove(user);
            post.setLikers(userList);
            postRepository.save(post);
            List<Post> postList = user.getLikedPosts();
            postList.remove(post);
            user.setLikedPosts(postList);
            userRepository.save(user);
        }
        return post;
    }

    @Override
    public List<UserDto> getLikers(Post post) {
        return post.getLikers().stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Post addMessageToPost(PostMessage postMessage, Post post) {

        post.setMessageText(postMessage.getMessage());
        postRepository.save(post);
        return post;
    }
}
