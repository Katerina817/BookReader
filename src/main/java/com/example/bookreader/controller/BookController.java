package com.example.bookreader.controller;

import com.example.bookreader.DTO.BookResponse;
import com.example.bookreader.DTO.CreateBookRequest;
import com.example.bookreader.Mapper.BookMapper;
import com.example.bookreader.entity.Book;
import com.example.bookreader.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    public BookResponse createBook(@RequestBody @Valid CreateBookRequest request) {
        Book book=bookService.addBook(
                request.getName(),
                request.getAuthor(),
                request.getDescription()
        );
        return BookMapper.toResponse(book);
    }
}
