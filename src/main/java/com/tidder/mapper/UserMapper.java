package com.tidder.mapper;

import com.tidder.domains.User;
import com.tidder.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getMail(),
                user.getPostsCount(),
                user.getSubscribersCount(),
                user.getSubscriptionsCount(),
                user.getProfilePicturePath(),
                user.getFriends(),
                user.getPostList()
        );
    }
}
