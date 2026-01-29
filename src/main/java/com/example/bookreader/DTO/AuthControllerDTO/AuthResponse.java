package com.example.bookreader.DTO.AuthControllerDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponse {
    private String login;
    private UUID userId;
}
