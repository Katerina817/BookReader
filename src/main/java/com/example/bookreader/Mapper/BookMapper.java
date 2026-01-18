package com.example.bookreader.Mapper;

import com.example.bookreader.DTO.BookControllerDTO.BookResponse;
import com.example.bookreader.entity.Book;

import java.util.stream.Collectors;

public class BookMapper {
    public static BookResponse toResponse(Book book) {
        BookResponse dto = new BookResponse();
        dto.setUuid(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setGenres(book.getGenres()
                .stream()
                .map(g->g.getName())
                .collect(Collectors.toSet())
        );
        return dto;
    }
}
