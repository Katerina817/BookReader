package com.example.bookreader.service;

import com.example.bookreader.DTO.ReadingControllerDTO.Response.BaseReadingResponse;
import com.example.bookreader.entity.Book;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.enums.ReadingStatus;
import com.example.bookreader.entity.User;
import com.example.bookreader.enums.ReadingViewType;
import com.example.bookreader.mapper.ReadingMapper;
import com.example.bookreader.repository.ReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final UserService userService;
    private final BookService bookService;
    private final FriendshipService friendshipService;

    public ReadingService(ReadingRepository readingRepository, UserService userService, BookService bookService, FriendshipService friendshipService) {
        this.readingRepository = readingRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.friendshipService = friendshipService;
    }
    public Reading getReadingForOwner(UUID readingId) {
        User user=getCurrentUser();
        Reading reading = getReadingEntity(readingId);
        if(!reading.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Reading user id not match");
        }
        return reading;
    }
    public Reading getReadingForViewer(UUID readingId) {
        User viewer=getCurrentUser();
        Reading reading = getReadingEntity(readingId);
        if(!canViewReading(reading,viewer)){
            throw new RuntimeException("Access denied");
        }
        return reading;
    }
    private Reading getReadingEntity(UUID readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(()->new RuntimeException("Reading not found"));
    }

    public List<? extends BaseReadingResponse> getMyReadings() {
        User user=getCurrentUser();
        return getUserReadings(user.getId());
    }
    /*public List<Reading> getUserReadings(UUID ownerId) {
        User viewer=getCurrentUser();   //АНАЛОГИЧНО
        User owner=userService.getUserById(ownerId);
        if(viewer.getId().equals(owner.getId())){
            return readingRepository.findByUser(owner);
        }
        return readingRepository.findByUserAndPrivateReadingFalse(owner);
    }*/
    public List<? extends BaseReadingResponse> getUserReadings(UUID ownerId) {
        User viewer=getCurrentUser();
        User owner=userService.getUserById(ownerId);
        //если тот кто просматривает и есть владелец reading
        if(viewer.getId().equals(owner.getId())){
            return readingRepository.findByUser(owner).
                    stream()
                    .map(reading -> ReadingMapper.map(reading, ReadingViewType.OWNER))
                    .collect(Collectors.toList());
        }
        if(friendshipService.existsFriendship(owner, viewer)){
            return readingRepository.findByUserAndPrivateReadingFalse(owner).
                    stream()
                    .map(reading -> ReadingMapper.map(reading,ReadingViewType.FRIEND))
                    .collect(Collectors.toList());
        }
        return readingRepository.findByUserAndPrivateReadingFalse(owner)
                .stream()
                .map(reading -> ReadingMapper.map(reading,ReadingViewType.VIEWER))
                .collect(Collectors.toList());
    }

    public Reading createReading(UUID bookId,ReadingStatus readingStatus,LocalDateTime dateStartOfReading, Boolean privateReading) {
        User user=getCurrentUser();
        Book book=bookService.getBookById(bookId);
        if(readingRepository.existsReadingByUserAndBook(user,book)){
            throw new RuntimeException("Reading already exists");
        }
        Reading reading=new Reading();
        reading.setUser(user);
        reading.setBook(book);
        reading.setStatus(readingStatus);
        if (book.isPrivate()==Boolean.TRUE){
            reading.setPrivateReading(true);
        }else{
            reading.setPrivateReading(privateReading);
        }
        if(dateStartOfReading!=null){
            reading.setDateStartOfReading(dateStartOfReading);
        }else if(readingStatus == ReadingStatus.READING){
            reading.setDateStartOfReading(LocalDateTime.now());
        }
        return readingRepository.save(reading);
    }
    public Reading updateReading(UUID readingId, ReadingStatus newStatus, LocalDateTime dateStartOfReading, LocalDateTime dateEndOfReading,Integer evaluationOfCharacter, Integer evaluationOfPlot, Integer evaluationOfEmotions,Integer qualityOfDialog, Integer atmosphere, String review, Boolean privateReading) {
       Reading reading=getReadingForOwner(readingId);
       Book book=bookService.getBookById(reading.getBook().getId());
        if (book.isPrivate()==Boolean.TRUE){
            reading.setPrivateReading(true);
        }else if (privateReading!=null){
            reading.setPrivateReading(privateReading);
        }
       if(newStatus!=null) {
           reading.setStatus(newStatus);
           if (newStatus == ReadingStatus.READING)
               reading.setDateStartOfReading(Objects.requireNonNullElseGet(dateStartOfReading, LocalDateTime::now));
           if (newStatus == ReadingStatus.FINISHED || newStatus == ReadingStatus.DROPPED)
               reading.setDateFinishOfReading(Objects.requireNonNullElseGet(dateEndOfReading, LocalDateTime::now));
       }
       if(isRatingAllowed(reading,newStatus)){
           if(evaluationOfCharacter==null && evaluationOfPlot==null && evaluationOfEmotions==null && qualityOfDialog==null && atmosphere==null){
               throw new RuntimeException("There is no evaluations");
           }
           List<Integer> numbers=new ArrayList<>();
           reading.setEvaluationOfCharacter(evaluationOfCharacter);
           numbers.add(evaluationOfCharacter);
           reading.setEvaluationOfPlot(evaluationOfPlot);
           numbers.add(evaluationOfPlot);
           reading.setEvaluationOfEmotions(evaluationOfEmotions);
           numbers.add(evaluationOfEmotions);
           reading.setQualityOfDialog(qualityOfDialog);
           numbers.add(qualityOfDialog);
           reading.setAtmosphere(atmosphere);
           numbers.add(atmosphere);
           if(review!=null) {
               reading.setReview(review);
           }
           reading.setFinalMark(numbers.stream().mapToInt(Integer::intValue).average().getAsDouble());
       }

       return readingRepository.save(reading);
    }
    public void deleteReading(UUID readingId) {
        Reading reading=getReadingForOwner(readingId);
        readingRepository.delete(reading);
    }
    private boolean isRatingAllowed(Reading reading, ReadingStatus newStatus) {
        ReadingStatus status=newStatus!=null?newStatus:reading.getStatus();
        return status==ReadingStatus.FINISHED || status==ReadingStatus.DROPPED;
    }
    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public boolean canViewReading(Reading reading, User viewer) {
        User owner=reading.getUser();
        //если viewer - владелец reading
        if(viewer.getId().equals(owner.getId())) {
            return true;
        }
        //если reading публичный, могут смотреть все
        return !reading.getPrivateReading();
    }

    /*public List<Reading> getReadingMarkByBook(Book book) {
        readingRepository.findByBookAndPrivateReadingFalse(book);
    }*/


}
