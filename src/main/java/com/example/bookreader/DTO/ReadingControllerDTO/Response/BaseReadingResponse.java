package com.example.bookreader.DTO.ReadingControllerDTO.Response;

import com.example.bookreader.enums.ReadingStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class BaseReadingResponse {
    private UUID id;
    private ReadingStatus readingStatus;

    private UUID bookId;
    private String name;
    private String author;

    private UUID userId;
    private String login;

    private List<String> bookGenres;

}
