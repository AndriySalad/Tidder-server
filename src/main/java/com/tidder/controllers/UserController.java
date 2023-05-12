package com.tidder.controllers;

import com.tidder.domains.User;
import com.tidder.dto.UserDto;
import com.tidder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public User showProfile(){
        return userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    UserDto getById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PutMapping("/profile/addavatar")
    UserDto addAvatar(@RequestBody MultipartFile multipartFile) throws IOException {
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userService.addAvatar(multipartFile, user);
    }

    @PostMapping("/{id}/subscribe")
    UserDto subscribe(@PathVariable Long id){
        User subscriber = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserDto subscriptionUser = userService.findById(id);
        return userService.makeSubscribe(subscriber, subscriptionUser);
    }

    @GetMapping("/subscription")
    public List<UserDto> getMySubscription(){
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userService.getMySubscriptions(user);
    }

    @GetMapping("/{id}/subscription")
    public List<UserDto> getUsersSubscription(@PathVariable Long id){
        UserDto user = userService.findById(id);
        return userService.getSubscriptions(user);
    }
}
