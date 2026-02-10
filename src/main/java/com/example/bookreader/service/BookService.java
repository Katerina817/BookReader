package com.example.bookreader.service;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final UserService userService;
    public BookService(BookRepository bookRepository,GenreService genreService,UserService userService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.userService = userService;
    }
    public List<Book> findByName(String name) {
        return bookRepository.findByNameContainingIgnoreCase(getCurrentUser(), name);
    }
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(getCurrentUser(),author);
    }
    public List<Book> findByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(getCurrentUser(),name, author);
    }
    public Book getBookById(UUID id) {
        User user=getCurrentUser();
        Book book=bookRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Book not found"));
        if(book.isPrivate() &&
                (book.getUser()==null || !book.getUser().getId().equals(user.getId()))){
            throw new RuntimeException("Access denied");
        }
        return book;
    }

    public void deleteBook(UUID bookId) {
        Book book=getBookById(bookId);
        bookRepository.delete(book);
    }
    public Book updateBook(UUID id,String name, String author, String description, Boolean isPrivate) {
        Book book=getBookById(id);
        if(name!=null) book.setName(name);
        if(author!=null) book.setAuthor(author);
        if(description!=null) book.setDescription(description);
        if(isPrivate!=null) {
            book.setPrivate(isPrivate);
            if (isPrivate) {
                book.setUser(getCurrentUser());
            }
            else{
                book.setUser(null);
            }
        }

        if(!(name==null || name.isBlank()) && !(author==null || author.isBlank())) {
            if(bookRepository.existsByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(getCurrentUser(),name, author)) {
                throw new RuntimeException("Book already exists");
            }
        };
        return bookRepository.save(book);
    }
    public Book addBook(String name, String author, String description, Boolean isPrivate) {
        Book book=new Book();
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Book name is required");
        }
        if (author == null || author.isBlank()) {
            throw new RuntimeException("Author is required");
        }
        if(bookRepository.existsByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(getCurrentUser(),name, author)) {
            throw new RuntimeException("Book already exists");
        }
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrivate(Objects.requireNonNullElse(isPrivate, Boolean.FALSE));
        if (isPrivate!=null && isPrivate) {
            book.setUser(getCurrentUser());
        }
        return bookRepository.save(book);
    }
    public Book addGenreToBook(UUID bookId, UUID genreId) {
        Book book=getBookById(bookId);
        Genre genre=genreService.getGenreById(genreId);
        if(book.getGenres().contains(genre)) {
            throw new RuntimeException("Genre already exists");
        }
        book.getGenres().add(genre);
        return bookRepository.save(book);
    }
    public Book deleteGenreFromBook(UUID bookId, UUID genreId) {
        Book book=getBookById(bookId);
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
    private User getCurrentUser() {return userService.getCurrentUser();}
}
