package com.example.bookreader.service;

import com.example.bookreader.DTO.AuthControllerDTO.LoginRequest;
import com.example.bookreader.DTO.AuthControllerDTO.RegisterRequest;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(RegisterRequest registerRequest) {
        if(userRepository.existsByLogin(registerRequest.getLogin())) {
            throw new RuntimeException("Login already exists");
        }
        if(registerRequest.getEmail()!=null && userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );
        user.setEmail(registerRequest.getEmail());
        return userRepository.save(user);
    }
    public User login(LoginRequest loginRequest) {
        User user= userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Login does not exist"));
        if(!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return user;
    }
}
