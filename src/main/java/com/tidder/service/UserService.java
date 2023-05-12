package com.tidder.service;


import com.tidder.domains.User;
import com.tidder.dto.UserDto;
import com.tidder.utils.AuthenticationRequest;
import com.tidder.utils.AuthenticationResponse;
import com.tidder.utils.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User getByEmail(String email);

    List<UserDto> getAll();

    UserDto findById(Long id);

    UserDto addAvatar(MultipartFile multipartFile, User user) throws IOException;
}
