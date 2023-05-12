package com.tidder.dto;

import com.tidder.domains.Comment;
import com.tidder.domains.Post;
import com.tidder.domains.Role;
import com.tidder.domains.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UserDto(
        Long id,
        String userName,
        String mail,
        Long postsCount,
        Long subscribersCount,
        Long subscriptionsCount,
        String profilePicturePath,
        Set<User> friends,
        List<Post> postList
) {
}
