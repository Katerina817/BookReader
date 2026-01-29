package com.example.bookreader.DTO.AuthControllerDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String login;
    @Email
    private String email;
}
