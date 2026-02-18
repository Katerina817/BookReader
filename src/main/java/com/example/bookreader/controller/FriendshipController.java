package com.example.bookreader.controller;

import com.example.bookreader.DTO.FriendshopControllerDTO.CreateFriendshipRequest;
import com.example.bookreader.DTO.FriendshopControllerDTO.FriendshipResponse;
import com.example.bookreader.entity.Friendship;
import com.example.bookreader.service.FriendshipService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public FriendshipResponse createFriendship(
            @RequestBody @Valid CreateFriendshipRequest request) {
        Friendship friendship=friendshipService.createFriendshipRequest(
                request.getAddresseeId()
        );
        return friendshipService.toFriendshipResponse(friendship);
    }

    @PostMapping("/{requestId}/accept")
    @PreAuthorize("hasRole('USER')")
    public FriendshipResponse acceptFriendship(
            @PathVariable UUID requestId) {
        Friendship friendship=friendshipService.friendshipRequestAcception(
                requestId
        );
        return friendshipService.toFriendshipResponse(friendship);
    }

    @PostMapping("/{requestId}/reject")
    @PreAuthorize("hasRole('USER')")
    public FriendshipResponse rejectFriendship(
            @PathVariable UUID requestId) {
        Friendship friendship=friendshipService.friendshipRequestRejection(
                requestId
        );
        return friendshipService.toFriendshipResponse(friendship);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<FriendshipResponse> getMyFriends() {
        return friendshipService.getMyFriends()
                .stream()
                .map(friendshipService::toFriendshipResponse)
                .toList();
    }

    @DeleteMapping("/{friendsId}")
    @PreAuthorize("hasRole('USER')")
    public List<FriendshipResponse> deleteFriendship(
            @PathVariable UUID friendsId
    ) {
        return friendshipService.deleteFriend(friendsId)
                .stream()
                .map(friendshipService::toFriendshipResponse)
                .toList();
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('USER')")
    public List<FriendshipResponse> getUserFriends(
            @PathVariable UUID userId
    ) {
        return friendshipService.getUserFriends(userId)
                .stream()
                .map(friendshipService::toFriendshipResponse)
                .toList();
    }
}
