package com.example.bookreader.repository;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("""
    SELECT b FROM Book b
    WHERE (b.isPrivate=false OR b.user=:user)
    AND lower(b.name) LIKE lower(concat('%', :name, '%'))
    """)
    List<Book> findByNameContainingIgnoreCase(@Param("user") User user, @Param("name")String name);
    @Query("""
    SELECT b FROM Book b
    WHERE (b.isPrivate=false OR b.user=:user)
    AND lower(b.author) LIKE lower(concat('%', :author, '%'))
    """)
    List<Book> findByAuthorContainingIgnoreCase(@Param("user") User user, @Param("author")String author);
    @Query("""
    SELECT COUNT(b) > 0 FROM Book b
    WHERE (b.isPrivate=false OR b.user=:user)
    AND (lower(b.name) LIKE lower(:name))
    AND lower(b.author) LIKE lower(:author)
    """)
    boolean existsByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            @Param("user")User user,
            @Param("name")String name,
            @Param("author")String author
    );
    @Query("""
    SELECT b FROM Book b
    WHERE (b.isPrivate=false OR b.user=:user)
    AND lower(b.name) LIKE lower(concat('%', :name, '%'))
    AND lower(b.author) LIKE lower(concat('%', :author, '%'))
    """)
    List<Book> findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            @Param("user")User user,
            @Param("name")String name,
            @Param("author")String author
    );
}
