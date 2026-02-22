package com.example.bookreader.repository;

import com.example.bookreader.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);

    boolean existsByName(String name);
}
