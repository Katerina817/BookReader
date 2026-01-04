package com.example.bookreader.repository;

import com.example.bookreader.entity.Note;
import com.example.bookreader.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByReading(Reading reading);
}
