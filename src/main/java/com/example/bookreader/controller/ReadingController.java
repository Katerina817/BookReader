package com.example.bookreader.controller;

import com.example.bookreader.DTO.ReadingControllerDTO.*;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.BaseReadingResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingOwnerResponse;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.entity.User;
import com.example.bookreader.mapper.ReadingMapper;
import com.example.bookreader.service.FriendshipService;
import com.example.bookreader.service.ReadingService;
import com.example.bookreader.service.UserService;
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
    private final UserService userService;
    private final FriendshipService friendshipService;

    public ReadingController(ReadingService readingService, UserService userService, FriendshipService friendshipService) {
        this.readingService = readingService;
        this.userService = userService;
        this.friendshipService = friendshipService;
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
    public List<? extends BaseReadingResponse> getUserReadings(
            @PathVariable UUID userId
    ) {
        User viewer=userService.getCurrentUser();

        return readingService.getUserReadings(userId)
                .stream()
                .map(reading -> {
                    if(reading.getUser().getId().equals(viewer.getId())) {
                        return ReadingMapper.toReadingOwnerResponse(reading);
                    }
                    if(friendshipService.existsFriendship(reading.getUser(), viewer)) {
                        return ReadingMapper.toReadingFriendResponse(reading);
                    }
                    return ReadingMapper.toReadingViewerResponse(reading);
                })
                .collect(Collectors.toList());
    }
}
