package com.example.bookreader.repository;

import com.example.bookreader.entity.Friendship;
import com.example.bookreader.enums.FriendshipStatus;
import com.example.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {

    List<Friendship> findByAddresseeOrRequesterAndStatus(User addressee, User requester, FriendshipStatus status);

    @Query("""
    SELECT count(f) FROM Friendship f
    WHERE (f.addressee = :user1 AND f.requester = :user2)OR
    (f.addressee = :user2 AND f.requester = :user1)
    """)
    Boolean existsBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);
}
