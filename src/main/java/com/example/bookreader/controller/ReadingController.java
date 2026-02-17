package com.example.bookreader.controller;

import com.example.bookreader.DTO.ReadingControllerDTO.CreateReadingRequest;
import com.example.bookreader.DTO.ReadingControllerDTO.ReadingOwnerResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.ReadingViewerResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.UpdateReadingRequest;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.mapper.ReadingMapper;
import com.example.bookreader.service.ReadingService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/readings")
public class ReadingController {
    private final ReadingService readingService;

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ReadingOwnerResponse> getMyReadings() {
        return readingService.getAllReadingsByUser()
                .stream()
                .map(ReadingMapper::toReadingOwnerResponse)
                .collect(Collectors.toList());
    }
    @GetMapping("/users/{userId}")
    public List<ReadingViewerResponse> getUserReadings(
            @PathVariable UUID userId
    ) {
        return readingService.getUserReadingsForViewer(userId)
                .stream()
                .map(ReadingMapper::toReadingViewerResponse)
                .collect(Collectors.toList());
    }

}
