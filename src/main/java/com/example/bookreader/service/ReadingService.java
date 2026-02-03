package com.example.bookreader.service;

import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.ReadingStatus;
import com.example.bookreader.entity.User;
import com.example.bookreader.repository.ReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final UserService userService;
    private final BookService bookService;

    public ReadingService(ReadingRepository readingRepository, UserService userService, BookService bookService) {
        this.readingRepository = readingRepository;
        this.userService = userService;
        this.bookService = bookService;
    }
    public Reading getReadingById(UUID readingId) {
        User user=getCurrentUser();
        Reading reading = readingRepository.findById(readingId)
                .orElseThrow(()->new RuntimeException("Reading not found"));
        if(!reading.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Reading user id not match");
        }
        return reading;
    }
    public List<Reading> getAllReadingsByUser() {
        User user=getCurrentUser();
        return readingRepository.findByUser(user);
    }
    public Reading createReading(UUID bookId,ReadingStatus readingStatus,LocalDateTime dateStartOfReading) {
        User user=getCurrentUser();
        Book book=bookService.getBookById(bookId);
        if(readingRepository.existsReadingByUserAndBook(user,book)){
            throw new RuntimeException("Reading already exists");
        }
        Reading reading=new Reading();
        reading.setUser(user);
        reading.setBook(book);
        reading.setStatus(readingStatus);
        if(dateStartOfReading!=null){
            reading.setDateStartOfReading(dateStartOfReading);
        }else if(readingStatus == ReadingStatus.READING){
            reading.setDateStartOfReading(LocalDateTime.now());
        }
        return readingRepository.save(reading);
    }
    public Reading updateReading(UUID readingId, ReadingStatus newStatus, LocalDateTime dateStartOfReading, LocalDateTime dateEndOfReading,Integer evaluationOfCharacter, Integer evaluationOfPlot, Integer evaluationOfEmotions,Integer qualityOfDialog, Integer atmosphere, String review) {
       Reading reading=getReadingById(readingId);
       if(newStatus!=null){
           reading.setStatus(newStatus);
           if(newStatus==ReadingStatus.READING)reading.setDateStartOfReading(LocalDateTime.now());
           if(newStatus==ReadingStatus.FINISHED)reading.setDateFinishOfReading(LocalDateTime.now());
       }
       if(dateStartOfReading!=null) {
           reading.setDateStartOfReading(dateStartOfReading);
       }
       if(dateEndOfReading!=null) {
           reading.setDateFinishOfReading(dateEndOfReading);
       }
       if(evaluationOfCharacter!=null && isRatingAllowed(reading,newStatus)) {
           reading.setEvaluationOfCharacter(evaluationOfCharacter);
       }
       if(evaluationOfPlot!=null && isRatingAllowed(reading,newStatus)) {
           reading.setEvaluationOfPlot(evaluationOfPlot);
       }
       if(evaluationOfEmotions!=null && isRatingAllowed(reading,newStatus)) {
           reading.setEvaluationOfEmotions(evaluationOfEmotions);
       }
       if(qualityOfDialog!=null && isRatingAllowed(reading,newStatus)) {
           reading.setQualityOfDialog(qualityOfDialog);
       }
       if(atmosphere!=null && isRatingAllowed(reading,newStatus)) {
           reading.setAtmosphere(atmosphere);
       }
       if(review!=null && isRatingAllowed(reading,newStatus)) {
           reading.setReview(review);
       }
       return readingRepository.save(reading);
    }
    public void deleteReading(UUID readingId) {
        Reading reading=getReadingById(readingId);
        readingRepository.delete(reading);
    }
    private boolean isRatingAllowed(Reading reading, ReadingStatus newStatus) {
        ReadingStatus status=newStatus!=null?newStatus:reading.getStatus();
        return status==ReadingStatus.FINISHED || status==ReadingStatus.DROPPED;
    }
    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
