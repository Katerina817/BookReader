package com.example.bookreader.entity;

import com.example.bookreader.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private User addressee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private FriendshipStatus status=FriendshipStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Friendship() {
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

}
