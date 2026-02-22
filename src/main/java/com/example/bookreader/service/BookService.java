package com.example.bookreader.service;

import com.example.bookreader.DTO.BookControllerDTO.BookResponse;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.enums.ReadingStatus;
import com.example.bookreader.enums.SortDirection;
import com.example.bookreader.enums.BookSortType;
import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.entity.User;
import com.example.bookreader.mapper.BookMapper;
import com.example.bookreader.repository.BookRepository;
import lombok.Locked;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<BookResponse> findBooks(String name, String author, List<UUID> genreIds, BookSortType sortType, SortDirection sortDirection) {
        List<Book> books;
        User user = getCurrentUser();
        if(name!=null && author!=null){
            books = bookRepository.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(user,name, author);
        }
        else if(name!=null){
            books = bookRepository.findByNameContainingIgnoreCase(user, name);
        }
        else if(author!=null){
            books = bookRepository.findByAuthorContainingIgnoreCase(user,author);
        }
        else{
            books=bookRepository.findAllVisibleForUser(user);
        }

        if(genreIds!=null && !genreIds.isEmpty()){
            Set<Genre> genres = genreService.getGenresByIds(genreIds);
            books=books
                    .stream()
                    .filter(book -> book.getGenres().containsAll(genres))
                    .collect(Collectors.toList());
        }
        List<BookResponse>responses=books.stream().map(this::getBookResponse).toList();
        //сортировка
        if(sortType!=null){
            Comparator<BookResponse> comparator = getBookComparator(sortType, sortDirection);
            responses=responses.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }
        return responses;
    }

    private static Comparator<BookResponse> getBookComparator(BookSortType sortType, SortDirection sortDirection) {
        Comparator<BookResponse> comparator = switch (sortType){
            case NAME -> Comparator.comparing(BookResponse::getName);
            case AUTHOR -> Comparator.comparing(BookResponse::getAuthor);
            case MARK -> Comparator.comparing(BookResponse::getAverageMark,
                    Comparator.nullsLast(Double::compareTo));
        };

        if(sortDirection == SortDirection.DESC){
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private BookResponse getBookResponse(Book book) {
        List<Reading>publicReadings = book.getReadings()
                .stream()
                .filter(r->
                        !r.getPrivateReading()
                && (r.getStatus()== ReadingStatus.FINISHED ||r.getStatus()== ReadingStatus.DROPPED)
                && r.getFinalMark()!=null)
                .toList();
        Double averageMark=publicReadings
                .stream()
                .mapToDouble(Reading::getFinalMark)
                .average()
                .orElse(0.0);
        List<String>reviews=publicReadings
                .stream()
                .map(Reading::getReview)
                .filter(Objects::nonNull)
                .toList();
        return BookMapper.toResponse(
                book,
                averageMark,
                reviews,
                publicReadings.size());
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
    public BookResponse updateBook(UUID id,String name, String author, String description, Boolean isPrivate) {
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
        bookRepository.save(book);
        return getBookResponse(book);
    }
    public BookResponse addBook(String name, String author, String description, Boolean isPrivate) {
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
        bookRepository.save(book);
        return getBookResponse(book);
    }
    public BookResponse addGenreToBook(UUID bookId, UUID genreId) {
        Book book=getBookById(bookId);
        Genre genre=genreService.getGenreById(genreId);
        if(book.getGenres().contains(genre)) {
            throw new RuntimeException("Genre already exists");
        }
        book.getGenres().add(genre);
        bookRepository.save(book);
        return getBookResponse(book);
    }
    public BookResponse deleteGenreFromBook(UUID bookId, UUID genreId) {
        Book book=getBookById(bookId);
        Genre genre=genreService.getGenreById(genreId);
        if(!book.getGenres().contains(genre)) {
            throw new RuntimeException("Genre not assigned to this book");
        }
        book.getGenres().remove(genre);
        bookRepository.save(book);
        return getBookResponse(book);
    }
    public Set<Genre> getBookGenres(UUID bookId) {
        return getBookById(bookId).getGenres();
    }
    private User getCurrentUser() {return userService.getCurrentUser();}
}
