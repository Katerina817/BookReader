package com.example.bookreader.DTO;

import com.example.bookreader.entity.Genre;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BookResponse {
    private UUID uuid;
    private String name;
    private String author;
    private String description;
    private Set<String> genres;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }
}
