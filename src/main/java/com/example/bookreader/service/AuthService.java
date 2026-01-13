package com.example.bookreader.service;

import com.example.bookreader.entity.User;
import com.example.bookreader.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User register(String login, String password,String email) {
        if(userRepository.existsByLogin(login)) {
            throw new RuntimeException("Login already exists");
        }
        if(email!=null && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        return userRepository.save(user);
    }
    public User login(String login, String password) {
        User user= userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Login does not exist"));
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }
        return user;
    }
}
