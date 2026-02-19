package com.example.bookreader.service;

import com.example.bookreader.entity.Note;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final ReadingService readingService;
    private final UserService userService;
    private final FriendshipService friendshipService;

    public NoteService(NoteRepository noteRepository, ReadingService readingService, UserService userService, FriendshipService friendshipService) {
        this.noteRepository = noteRepository;
        this.readingService = readingService;
        this.userService = userService;
        this.friendshipService = friendshipService;
    }
    public Note createNote(UUID readingId, String content, String quote,Boolean privateNote) {
        if ((content==null || content.isBlank())&&(quote==null || quote.isBlank())) {
            throw new RuntimeException("Note cannot be empty");
        }
        Reading reading=readingService.getReadingForOwner(readingId);
        Note note=new Note();
        note.setContent(content);
        note.setQuote(quote);
        if (reading.getPrivateReading()==Boolean.TRUE){
            note.setPrivateNote(true);
        }else{
            note.setPrivateNote(privateNote);
        }
        note.setReading(reading);
        return noteRepository.save(note);
    }
    public List<Note> getNotesByReading(UUID readingId) {
        User viewer=getCurrentUser();
        //чтение приватное и viewer не владелец - нет доступа
        Reading reading=readingService.getReadingForViewer(readingId);
        User owner=reading.getUser();
        //если viewer владелец - видит все заметки
        if(viewer.getId().equals(owner.getId())) {
            return noteRepository.findByReading(reading);
        }
        if(!friendshipService.existsFriendship(viewer,owner)){
            throw new RuntimeException("Access denied");
        }
        //только друзья могут видеть заметки кроме самого владельца!!!
        return noteRepository.findByReadingAndPrivateNoteFalse(reading);
    }
    public Note updateNote(UUID readingId,UUID noteId, String content, String quote,Boolean privateNote) {
        if ((content==null || content.isBlank())&&(quote==null || quote.isBlank())) {
            deleteNote(readingId,noteId);
            throw new RuntimeException("Note cannot be empty");
        }
        Reading reading=readingService.getReadingForOwner(readingId);
        Note note=noteRepository.findById(noteId).
                orElseThrow(() -> new RuntimeException("Note not found"));
        if(!note.getReading().getId().equals(readingId)){
            throw new RuntimeException("Note does not belong to reading");
        }
        if (reading.getPrivateReading()==Boolean.TRUE){
            note.setPrivateNote(true);
        }else if(privateNote!=null){
            note.setPrivateNote(privateNote);
        }
        note.setContent(content);
        note.setQuote(quote);
        return noteRepository.save(note);
    }
    public void deleteNote(UUID readingId, UUID noteId) {
        readingService.getReadingForOwner(readingId);
        Note note=noteRepository.findById(noteId).
                orElseThrow(() -> new RuntimeException("Note not found"));
        if(!note.getReading().getId().equals(readingId)){
            throw new RuntimeException("Note does not belong to reading");
        }
        noteRepository.delete(note);
    }
    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
