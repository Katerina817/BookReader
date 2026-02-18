package com.example.bookreader.DTO.FriendshopControllerDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class FriendshipResponse {
    private UUID addresseeId;
    private String login;
    private LocalDateTime createdAt;
}
