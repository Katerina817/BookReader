package com.example.bookreader.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String quote;

    private LocalDateTime dateOfCreation;
    @PrePersist
    protected void onCreate() {
        dateOfCreation = LocalDateTime.now();
    }
    @ManyToOne
    @JoinColumn(name = "reading_id", nullable = false)
    private Reading reading;

    @Column(nullable = false)
    private Boolean privateNote=false;

    public Note() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }
    public String getQuote() {
        return quote;
    }
    public Boolean getPrivateNote() {
        return privateNote;
    }

    public void setPrivateNote(Boolean privateNote) {
        this.privateNote = privateNote;
    }
    public void setQuote(String quote) {
        this.quote = quote;
    }
}
