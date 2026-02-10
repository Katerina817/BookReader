package com.example.bookreader.controller;

import com.example.bookreader.DTO.NoteControllerDTO.CreateNoteRequest;
import com.example.bookreader.DTO.NoteControllerDTO.NoteResponse;
import com.example.bookreader.DTO.NoteControllerDTO.UpdateNoteRequest;
import com.example.bookreader.mapper.NoteMapper;
import com.example.bookreader.entity.Note;
import com.example.bookreader.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/readings/{readingId}/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public NoteResponse createNote(
            @PathVariable UUID readingId,
            @RequestBody @Valid CreateNoteRequest request) {
        Note note = noteService.createNote(readingId, request.getContent(),request.getQuote());
        return NoteMapper.toResponse(note);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{noteId}")
    public void deleteNote(
            @PathVariable UUID readingId,
            @PathVariable UUID noteId) {
        noteService.deleteNote(readingId, noteId);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{noteId}")
    public NoteResponse updateNote(
            @PathVariable UUID readingId,
            @PathVariable UUID noteId,
            @RequestBody @Valid UpdateNoteRequest request){
        Note note=noteService.updateNote(readingId,noteId,request.getContent(),request.getQuote());
        return NoteMapper.toResponse(note);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<NoteResponse> getAllNotes(
            @PathVariable UUID readingId
    ){
        List<Note>notes=noteService.getNotesByReading(readingId);
        return notes.stream()
                .map(NoteMapper::toResponse)
                .collect(Collectors.toList());
    }
}
