package com.example.bookreader.DTO.AuthControllerDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String login;
}
