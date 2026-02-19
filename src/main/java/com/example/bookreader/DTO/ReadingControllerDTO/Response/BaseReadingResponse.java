package com.example.bookreader.DTO.ReadingControllerDTO.Response;

import com.example.bookreader.entity.ReadingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Data
@Setter
@Getter
public class BaseReadingResponse {
    private UUID id;
    private ReadingStatus readingStatus;
    private UUID bookId;
    private String name;
    private String author;
}
