package com.tidder.service;

import com.tidder.dao.PostRepository;
import com.tidder.dao.UserRepository;
import com.tidder.domains.Post;
import com.tidder.domains.User;
import com.tidder.dto.PostDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
    public boolean createdPost(PostDto postDto) {

        // Дописати створення нових постів.

        return false;
    }

    @Override
    @Transactional
    public boolean deletePost(Post post) {
        postRepository.delete(post);
        return true;
    }

    @Override
    public boolean updatePost(Post post, PostDto updatedPost) {
        post.setMessageText(updatedPost.getMessageText());

        // TODO обробку прикріплених файлів.

        return false;
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
