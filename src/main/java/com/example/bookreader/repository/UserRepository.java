package com.example.bookreader.repository;

import com.example.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

}
