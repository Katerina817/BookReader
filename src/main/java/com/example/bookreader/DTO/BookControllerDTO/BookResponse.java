package com.example.bookreader.DTO.BookControllerDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
public class BookResponse {
    private UUID id;
    private String name;
    private String author;
    private String description;
    private Boolean isPrivate;
    private Set<String> genres;

    private Double averageMark;
    private Integer numberOfPublicReadings;
    private List<String> reviews;
}
