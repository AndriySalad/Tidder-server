package com.tidder.service;

import com.tidder.dao.PostRepository;
import com.tidder.dao.UserRepository;
import com.tidder.domains.Post;
import com.tidder.domains.PostStatus;
import com.tidder.domains.User;
import com.tidder.dto.PostDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileUpload fileUpload;

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
    public boolean createdPost(PostDto postDto, String mail) throws IOException {

        User user = userRepository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String imageURL = fileUpload.uploadFile(postDto.getAttachment());
        Post post = Post.builder()
                .attachmentPath(imageURL)
                .postStatus(PostStatus.AVAILABLE)
                .countComments(0L)
                .messageText(postDto.getMessageText())
                .countLikes(0L)
                .user(user)
                .build();
        return true;
    }

    @Override
    @Transactional
    public boolean deletePost(Post post) {
        postRepository.delete(post);
        return true;
    }

    @Override
    @Transactional
    public boolean likedPost(Post post, User user) {
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
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public List<User> getLikers(Post post) {
        return post.getLikers();
    }
}
