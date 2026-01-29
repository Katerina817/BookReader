package com.example.bookreader.mapper;

import com.example.bookreader.DTO.AuthControllerDTO.AuthResponse;
import com.example.bookreader.entity.User;

public class AuthMapper {
    public static AuthResponse toResponse(User user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getId());
        authResponse.setLogin(user.getLogin());
        return authResponse;
    }
}
