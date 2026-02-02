package com.example.bookreader.controller;

import com.example.bookreader.DTO.BookControllerDTO.BookResponse;
import com.example.bookreader.DTO.BookControllerDTO.CreateBookRequest;
import com.example.bookreader.DTO.BookControllerDTO.UpdateBookRequest;
import com.example.bookreader.mapper.BookMapper;
import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PatchMapping("/{id}")
    public BookResponse updateBook(@PathVariable UUID id, @RequestBody UpdateBookRequest request) {
        Book book=bookService.updateBook(
                id,
                request.getName(),
                request.getAuthor(),
                request.getDescription()
        );
        return BookMapper.toResponse(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<BookResponse> searchBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author){
        List<Book> books;
        if(name!=null && author!=null){
            books = bookService.findByNameAndAuthor(name, author);
        }
        else if(name!=null){
            books = bookService.findByName(name);
        }
        else if(author!=null){
            books = bookService.findByAuthor(author);
        }
        else{
            books=List.of();
        }
        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    @PostMapping("/{id}/genres/{genreId}")
    public BookResponse addGenre(
            @PathVariable UUID id,
            @PathVariable UUID genreId) {
        Book book=bookService.addGenreToBook(id, genreId);
        return BookMapper.toResponse(book);
    }

    @DeleteMapping("{bookId}/genres/{genreId}")
    public BookResponse deleteGenre(
            @PathVariable UUID bookId,
            @PathVariable UUID genreId
    ){
        Book book=bookService.deleteGenreFromBook(bookId, genreId);
        return BookMapper.toResponse(book);
    }

    @GetMapping("/{bookId}/genres")
    public Set<String> getGenres(@PathVariable UUID bookId){
        Set<Genre> genres=bookService.getBookGenres(bookId);
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
    }
}
