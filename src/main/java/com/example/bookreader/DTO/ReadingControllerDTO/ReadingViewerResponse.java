package com.example.bookreader.DTO.ReadingControllerDTO;


import com.example.bookreader.entity.ReadingStatus;
import lombok.Data;

import java.util.UUID;
@Data
public class ReadingViewerResponse {
    private UUID id;
    private ReadingStatus readingStatus;
    private UUID bookId;
    private String name;
    private String author;
}
