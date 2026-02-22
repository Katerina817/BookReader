package com.example.bookreader.service;

import com.example.bookreader.DTO.FriendshopControllerDTO.FriendshipResponse;
import com.example.bookreader.entity.Friendship;
import com.example.bookreader.enums.FriendshipStatus;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
//TO DO добавить уникальное ограничение на уровне бд
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    public FriendshipService(FriendshipRepository friendshipRepository,UserService userService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
    }
    public Friendship findById(UUID id) {
        return friendshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find friendship"));
    }
    public Friendship createFriendshipRequest(UUID addresseeId) {
        User requester = getCurrentUser();
        User addressee=userService.getUserById(addresseeId);
        if (addresseeId.equals(requester.getId())) {
            throw new RuntimeException("You cannot add yourself to your friends");
        }
        if(friendshipRepository.existsBetweenUsers(addressee,requester)){
            throw new RuntimeException("Friendship already exists");
        }
        Friendship friendship = new Friendship();
        friendship.setRequester(getCurrentUser());
        friendship.setAddressee(addressee);
        return friendshipRepository.save(friendship);
    }

    public Friendship friendshipRequestAcception(UUID friendshipId) {
        Friendship friendship = findById(friendshipId);
        if(!friendship.getAddressee().equals(getCurrentUser())){
            throw new RuntimeException("Friendship does not address to the current user");
        }
        if(friendship.getStatus()!=FriendshipStatus.PENDING){
            throw new RuntimeException("Friendship is already accepted/rejected");
        }
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    public Friendship friendshipRequestRejection(UUID friendshipId) {
        Friendship friendship = findById(friendshipId);
        if(!friendship.getAddressee().equals(getCurrentUser())){
            throw new RuntimeException("Friendship does not address to the current user");
        }
        if(friendship.getStatus()!=FriendshipStatus.PENDING){
            throw new RuntimeException("Friendship is already accepted/rejected");
        }
        friendship.setStatus(FriendshipStatus.REJECTED);
        return friendshipRepository.save(friendship);
    }

    public List<Friendship> getMyFriends() {
        return friendshipRepository
                .findByAddresseeOrRequesterAndStatus(
                        getCurrentUser(),
                        getCurrentUser(),
                        FriendshipStatus.ACCEPTED);
    }

    public List<Friendship> getUserFriends(UUID userId) {
        if(!getCurrentUser().getId().equals(userId) && userService.getUserById(userId).getPrivateFriends().equals(Boolean.TRUE)){
            throw new RuntimeException("User has private friends");
        }
        return friendshipRepository.findByAddresseeOrRequesterAndStatus(userService.getUserById(userId),userService.getUserById(userId),FriendshipStatus.ACCEPTED);
    }

    public List<Friendship> deleteFriend(UUID friendshipId) {
        Friendship friendship = findById(friendshipId);
        User user = getCurrentUser();
        if (!friendship.getRequester().equals(user) && !friendship.getAddressee().equals(user)) {
            throw new RuntimeException("Not your friendship");
        }
        if (friendship.getStatus() != FriendshipStatus.ACCEPTED) {
            throw new RuntimeException("Friendship is not accepted");
        }
        friendshipRepository.deleteById(friendshipId);
        return getMyFriends();
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public User getFriendForCurrentUser(Friendship friendship) {
        if(friendship.getRequester().equals(getCurrentUser())){
            return friendship.getAddressee();
        }
        return friendship.getRequester();
    }
    public FriendshipResponse toFriendshipResponse(Friendship friendship) {
        User friend=getFriendForCurrentUser(friendship);

        FriendshipResponse dto = new FriendshipResponse();
        dto.setLogin(friend.getLogin());
        dto.setAddresseeId(friend.getId());
        dto.setCreatedAt(friendship.getCreatedAt());
        return dto;
    }

    public Boolean existsFriendship(User user1, User user2) {
        return friendshipRepository.existsBetweenUsers(user1,user2);
    }
}
