package com.example.bookreader.repository;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.ReadingStatus;
import com.example.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    List<Reading> findByUser(User user);

    List<Reading> findByUserAndStatus(User user, ReadingStatus status);

    Optional<Reading> findByUserAndBook(User user, Book book);

    boolean existsReadingByUserAndBook(User user, Book book);

    List<Reading> findByUserAndPrivateReadingFalse(User user);
}
