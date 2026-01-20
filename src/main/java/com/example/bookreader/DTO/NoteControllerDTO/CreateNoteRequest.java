package com.example.bookreader.DTO.NoteControllerDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNoteRequest {
    @NotBlank
    private String content;
}
