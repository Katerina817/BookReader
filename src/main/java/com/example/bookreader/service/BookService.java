package com.example.bookreader.service;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
    public BookService(BookRepository bookRepository,GenreService genreService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
    }
    public List<Book> findByName(String name) {
        return bookRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    public List<Book> findByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(name, author);
    }
    public void deleteBook(UUID bookId) {
        Book book=getBookById(bookId);
        bookRepository.delete(book);
    }
    public Book updateBook(UUID id,String name, String author, String description) {
        Book book=bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Book not found"));
        if(name!=null) book.setName(name);
        if(author!=null) book.setAuthor(author);
        if(description!=null) book.setDescription(description);
        if(!(name==null || name.isBlank()) && !(author==null || author.isBlank())) {
            if(!bookRepository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(name, author).isEmpty()) {
                throw new RuntimeException("Book already exists");
            }
        };
        return bookRepository.save(book);
    }
    public Book addBook(String name, String author, String description) {
        Book book=new Book();
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Book name is required");
        }
        if (author == null || author.isBlank()) {
            throw new RuntimeException("Author is required");
        }
        if(!bookRepository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(name, author).isEmpty()) {
            throw new RuntimeException("Book already exists");
        }
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        return bookRepository.save(book);
    }
    public Book addGenreToBook(UUID bookId, UUID genreId) {
        Book book=bookRepository.findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found"));
        Genre genre=genreService.getGenreById(genreId);
        if(book.getGenres().contains(genre)) {
            throw new RuntimeException("Genre already exists");
        }
        book.getGenres().add(genre);
        return bookRepository.save(book);
    }
    public Book getBookById(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found"));
    }
    public boolean existsBook(UUID bookId) {
        return bookRepository.existsById(bookId);
    }
    public Book deleteGenreFromBook(UUID bookId, UUID genreId) {
        Book book=bookRepository.findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found"));
        Genre genre=genreService.getGenreById(genreId);
        if(!book.getGenres().contains(genre)) {
            throw new RuntimeException("Genre not assigned to this book");
        }
        book.getGenres().remove(genre);
        return bookRepository.save(book);
    }
    public Set<Genre> getBookGenres(UUID bookId) {
        return getBookById(bookId).getGenres();
    }

}
