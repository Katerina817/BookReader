package com.example.bookreader.repository;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.enums.ReadingStatus;
import com.example.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    List<Reading> findByUser(User user);
    List<Reading> findByUserAndBookNameContainingIgnoreCase(User user, String bookName);
    List<Reading> findByUserAndBookAuthorContainingIgnoreCase(User user, String author);
    List<Reading> findByUserAndBookAuthorContainingIgnoreCaseAndBookNameContainingIgnoreCase(User user, String book_author, String book_name);


    List<Reading> findByUserAndStatus(User user, ReadingStatus status);

    Optional<Reading> findByUserAndBook(User user, Book book);

    boolean existsReadingByUserAndBook(User user, Book book);

    List<Reading> findByUserAndPrivateReadingFalse(User user);
    List<Reading> findByUserAndPrivateReadingFalseAndBookNameContainingIgnoreCase(User user, String bookName);
    List<Reading> findByUserAndPrivateReadingFalseAndBookAuthorContainingIgnoreCase(User user, String author);
    List<Reading> findByUserAndPrivateReadingFalseAndBookAuthorContainingIgnoreCaseAndBookNameContainingIgnoreCase(User user, String book_author, String book_name);

    List<Reading> findByBookAndPrivateReadingFalse(Book book);
}
