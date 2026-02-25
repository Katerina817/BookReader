package com.example.bookreader.service;

import com.example.bookreader.DTO.AuthControllerDTO.AuthResponse;
import com.example.bookreader.DTO.AuthControllerDTO.LoginRequest;
import com.example.bookreader.DTO.AuthControllerDTO.RegisterRequest;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.UserRepository;
import com.example.bookreader.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
    public AuthResponse login(LoginRequest loginRequest) {
        User user= userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Login does not exist"));
        if(!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token= jwtService.generateToken(user.getLogin());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUserId(user.getId());
        authResponse.setLogin(user.getLogin());
        return authResponse;
    }
}
