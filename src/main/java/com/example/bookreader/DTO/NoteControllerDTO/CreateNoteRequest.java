package com.example.bookreader.DTO.NoteControllerDTO;

import lombok.Data;

@Data
public class CreateNoteRequest {
    private String content;
    private String quote;
    private Boolean privateNote;

}
