package com.example.bookreader.controller;

import com.example.bookreader.DTO.ReadingControllerDTO.CreateReadingRequest;
import com.example.bookreader.DTO.ReadingControllerDTO.ReadingResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.UpdateReadingRequest;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.mapper.ReadingMapper;
import com.example.bookreader.service.ReadingService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ReadingResponse createReading(@RequestBody @Valid CreateReadingRequest request) {
         Reading reading= readingService.createReading(
                 request.getBookId(),
                 request.getReadingStatus(),
                 request.getDateStartOfReading());
         return ReadingMapper.toReadingResponse(reading);
    }

    @DeleteMapping("/{id}")
    public void deleteReading(@PathVariable UUID id) {
        readingService.deleteReading(id);
    }

    @PatchMapping("/{id}")
    public ReadingResponse updateReading(
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
                request.getReview()
        );
        return ReadingMapper.toReadingResponse(reading);
    }

    @GetMapping
    public List<ReadingResponse> getAllReadingsByUser() {
        return readingService.getAllReadingsByUser()
                .stream()
                .map(ReadingMapper::toReadingResponse)
                .collect(Collectors.toList());
    }
}
