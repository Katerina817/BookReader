package com.example.bookreader.repository;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            String name,
            String author
    );

}
