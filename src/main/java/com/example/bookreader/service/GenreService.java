package com.example.bookreader.service;

import com.example.bookreader.entity.Genre;
import com.example.bookreader.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    public Genre createGenre(String name) {
        if(genreRepository.existsByName(name)) {
            throw new RuntimeException("Genre already exists");
        }
        Genre genre=new Genre();
        if(name==null || name.isBlank()) {
            throw new RuntimeException("Genre name is required");
        }
        genre.setName(name);
        return genreRepository.save(genre);
    }
    public Genre getGenreById(UUID genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
    }

    public Set<Genre> getGenresByIds(List<UUID> genreIds) {
        Set<Genre> genres=new HashSet<>();
        for(UUID genreId : genreIds) {
            genres.add(getGenreById(genreId));
        }
        return genres;
    }

    public Genre findGenreByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
    }
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
