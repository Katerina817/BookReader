package com.example.bookreader.mapper;

import com.example.bookreader.DTO.BookControllerDTO.BookResponse;
import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.enums.ReadingStatus;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookResponse toResponse(Book book, Double avgMark, List<String> reviews,Integer numberOfPublicReadings) {
        BookResponse dto = new BookResponse();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setIsPrivate(book.isPrivate());
        dto.setDescription(book.getDescription());
        dto.setGenres(book.getGenres()
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toSet())
        );

        dto.setReviews(reviews);
        dto.setAverageMark(avgMark);
        dto.setNumberOfPublicReadings(numberOfPublicReadings);
        return dto;
    }
}
