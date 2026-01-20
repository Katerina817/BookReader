package com.example.bookreader.DTO.NoteControllerDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class NoteResponse {
    private UUID id;
    private String content;
    private LocalDateTime dateOfCreation;
    private UUID readingId;
}
