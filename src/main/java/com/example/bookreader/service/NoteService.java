package com.example.bookreader.service;

import com.example.bookreader.entity.Note;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final ReadingService readingService;
    public NoteService(NoteRepository noteRepository, ReadingService readingService) {
        this.noteRepository = noteRepository;
        this.readingService = readingService;
    }
    public Note createNote(UUID readingId, String content, String quote) {
        if ((content==null || content.isBlank())&&(quote==null || quote.isBlank())) {
            throw new RuntimeException("Note cannot be empty");
        }
        Reading reading=readingService.getReadingById(readingId);
        Note note=new Note();
        note.setContent(content);
        note.setQuote(quote);
        note.setReading(reading);
        return noteRepository.save(note);
    }
    public List<Note> getNotesByReading(UUID readingId) {
        Reading reading=readingService.getReadingById(readingId);
        return noteRepository.findByReading(reading);
    }
    public Note updateNote(UUID readingId,UUID noteId, String content, String quote) {
        if ((content==null || content.isBlank())&&(quote==null || quote.isBlank())) {
            deleteNote(readingId,noteId);
        }
        readingService.getReadingById(readingId);
        Note note=noteRepository.findById(noteId).
                orElseThrow(() -> new RuntimeException("Note not found"));
        if(!note.getReading().getId().equals(readingId)){
            throw new RuntimeException("Note does not belong to reading");
        }
        note.setContent(content);
        return noteRepository.save(note);
    }
    public void deleteNote(UUID readingId, UUID noteId) {
        readingService.getReadingById(readingId);
        Note note=noteRepository.findById(noteId).
                orElseThrow(() -> new RuntimeException("Note not found"));
        if(!note.getReading().getId().equals(readingId)){
            throw new RuntimeException("Note does not belong to reading");
        }
        noteRepository.delete(note);
    }
}
