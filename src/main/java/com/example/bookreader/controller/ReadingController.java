package com.example.bookreader.controller;

import com.example.bookreader.DTO.ReadingControllerDTO.*;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.BaseReadingResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingOwnerResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingViewerResponse;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.User;
import com.example.bookreader.mapper.ReadingMapper;
import com.example.bookreader.service.BookService;
import com.example.bookreader.service.FriendshipService;
import com.example.bookreader.service.ReadingService;
import com.example.bookreader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/readings")
public class ReadingController {
    private final ReadingService readingService;
    private final BookService bookService;

    public ReadingController(ReadingService readingService, BookService bookService) {
        this.readingService = readingService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ReadingOwnerResponse createReading(@RequestBody @Valid CreateReadingRequest request) {
         Reading reading= readingService.createReading(
                 request.getBookId(),
                 request.getReadingStatus(),
                 request.getDateStartOfReading(),
                 request.getPrivateReading());
         return ReadingMapper.toReadingOwnerResponse(reading);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public void deleteReading(@PathVariable UUID id) {
        readingService.deleteReading(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ReadingOwnerResponse updateReading(
            @PathVariable UUID id,
            @RequestBody UpdateReadingRequest request) {
        Reading reading = readingService.updateReading(
                id,
                request.getReadingStatus(),
                request.getDateStartOfReading(),
                request.getDateFinishOfReading(),
                request.getEvaluationOfCharacter(),
                request.getEvaluationOfPlot(),
                request.getEvaluationOfEmotions(),
                request.getQualityOfDialog(),
                request.getAtmosphere(),
                request.getReview(),
                request.getPrivateReading()
        );
        return ReadingMapper.toReadingOwnerResponse(reading);
    }

    /*@PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<? extends BaseReadingResponse> getMyReadings() {
        return readingService.getMyReadings();
    }*/
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<? extends BaseReadingResponse> getUserReadings(
            @RequestParam(required = false) UUID userId
    ) {
        return readingService.getUserReadings(userId);
    }


    /*@PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ReadingViewerResponse> getReadingsByBook(@PathVariable UUID bookId) {
        return readingService.getReadingsByBook(bookService.getBookById(bookId))
                .stream()
                .map(ReadingMapper::toReadingViewerResponse)
                .collect(Collectors.toList());
    }*/
}
