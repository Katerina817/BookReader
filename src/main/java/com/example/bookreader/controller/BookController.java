package com.example.bookreader.controller;

import com.example.bookreader.DTO.BookControllerDTO.BookResponse;
import com.example.bookreader.DTO.BookControllerDTO.CreateBookRequest;
import com.example.bookreader.DTO.BookControllerDTO.UpdateBookRequest;
import com.example.bookreader.enums.SortDirection;
import com.example.bookreader.enums.BookSortType;
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
        return bookService.addBook(
                request.getName(),
                request.getAuthor(),
                request.getDescription(),
                request.getIsPrivate()
        );
    }

    @PatchMapping("/{id}")
    public BookResponse updateBook(
            @PathVariable UUID id,
            @RequestBody UpdateBookRequest request) {
        return  bookService.updateBook(
                id,
                request.getName(),
                request.getAuthor(),
                request.getDescription(),
                request.getIsPrivate()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<BookResponse> searchBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) List<UUID> genreIds,
            @RequestParam(required = false) BookSortType sortType,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection){
        return bookService.findBooks(name,author,genreIds,sortType,sortDirection);
    }

    @PostMapping("/{id}/genres/{genreId}")
    public BookResponse addGenre(
            @PathVariable UUID id,
            @PathVariable UUID genreId) {
        return bookService.addGenreToBook(id, genreId);
    }

    @DeleteMapping("{bookId}/genres/{genreId}")
    public BookResponse deleteGenre(
            @PathVariable UUID bookId,
            @PathVariable UUID genreId
    ){
        return bookService.deleteGenreFromBook(bookId, genreId);
    }

    @GetMapping("/{bookId}/genres")
    public Set<String> getGenres(@PathVariable UUID bookId){
        Set<Genre> genres=bookService.getBookGenres(bookId);
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
    }
}
