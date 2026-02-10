package com.example.bookreader.DTO.BookControllerDTO;
import java.util.Set;
import java.util.UUID;

public class BookResponse {
    private UUID id;
    private String name;
    private String author;
    private String description;
    private Boolean isPrivate;
    private Set<String> genres;

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
    public UUID getUuid() {
        return id;
    }

    public void setUuid(UUID id) {
        this.id = id;
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
