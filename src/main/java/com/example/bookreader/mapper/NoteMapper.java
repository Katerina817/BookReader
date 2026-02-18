package com.example.bookreader.mapper;

import com.example.bookreader.DTO.NoteControllerDTO.NoteResponse;
import com.example.bookreader.entity.Note;

public class NoteMapper {
    public static NoteResponse toResponse(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setContent(note.getContent());
        response.setReadingId(note.getReading().getId());
        response.setQuote(note.getQuote());
        response.setPrivateNote(note.getPrivateNote());
        response.setDateOfCreation(note.getDateOfCreation());
        return response;
    }
}
