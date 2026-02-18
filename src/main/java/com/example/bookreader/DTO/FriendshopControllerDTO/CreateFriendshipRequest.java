package com.example.bookreader.DTO.FriendshopControllerDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class CreateFriendshipRequest {

    @NotNull
    private UUID addresseeId;
}
