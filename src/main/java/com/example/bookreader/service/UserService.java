package com.example.bookreader.service;

import com.example.bookreader.entity.User;
import com.example.bookreader.repository.UserRepository;
import com.example.bookreader.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    public UserService(UserRepository userRepository, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }
    public User getCurrentUser() {
        String login= securityUtils.getCurrentUser().getLogin();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
