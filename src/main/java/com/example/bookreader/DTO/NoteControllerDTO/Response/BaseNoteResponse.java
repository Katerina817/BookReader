package com.example.bookreader.DTO.NoteControllerDTO.Response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
@Getter
public class BaseNoteResponse {
    private UUID id;
    private String content;
    private String quote;
    private LocalDateTime dateOfCreation;
    private UUID readingId;
}
