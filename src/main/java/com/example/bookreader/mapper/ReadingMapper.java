package com.example.bookreader.mapper;

import com.example.bookreader.DTO.ReadingControllerDTO.Response.BaseReadingResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingFriendResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingOwnerResponse;
import com.example.bookreader.DTO.ReadingControllerDTO.Response.ReadingViewerResponse;
import com.example.bookreader.entity.Genre;
import com.example.bookreader.entity.Reading;
import com.example.bookreader.enums.ReadingViewType;

public class ReadingMapper {
    public static BaseReadingResponse map(Reading reading, ReadingViewType viewType) {
        return switch (viewType) {
            case OWNER -> toReadingOwnerResponse(reading);
            case VIEWER -> toReadingViewerResponse(reading);
            case FRIEND -> toReadingFriendResponse(reading);
        };
    }

    public static ReadingOwnerResponse toReadingOwnerResponse(Reading reading) {
        ReadingOwnerResponse dto = new ReadingOwnerResponse();
        dto.setReadingStatus(reading.getStatus());
        dto.setDateStartOfReading(reading.getDateStartOfReading());
        dto.setDateFinishOfReading(reading.getDateFinishOfReading());
        dto.setBookId(reading.getBook().getId());
        dto.setId(reading.getId());
        dto.setFinalMark(reading.getFinalMark());
        dto.setUserId(reading.getUser().getId());
        dto.setLogin(reading.getUser().getLogin());
        dto.setName(reading.getBook().getName());
        dto.setAuthor(reading.getBook().getAuthor());
        dto.setPrivateReading(reading.getPrivateReading());
        dto.setEvaluationOfCharacter(reading.getEvaluationOfCharacter());
        dto.setEvaluationOfPlot(reading.getEvaluationOfPlot());
        dto.setEvaluationOfEmotions(reading.getEvaluationOfEmotions());
        dto.setQualityOfDialog(reading.getQualityOfDialog());
        dto.setAtmosphere(reading.getAtmosphere());
        dto.setReview(reading.getReview());
        dto.setNotes(reading.getNotes()
                .stream()
                .map(NoteMapper::toResponse)
                .toList());
        dto.setBookGenres(reading.getBook().getGenres()
                .stream()
                .map(Genre::getId)
                .toList());
        return dto;
    }
    public static ReadingFriendResponse toReadingFriendResponse(Reading reading) {
        ReadingFriendResponse dto = new ReadingFriendResponse();
        dto.setReadingStatus(reading.getStatus());
        dto.setDateStartOfReading(reading.getDateStartOfReading());
        dto.setDateFinishOfReading(reading.getDateFinishOfReading());
        dto.setBookId(reading.getBook().getId());
        dto.setUserId(reading.getUser().getId());
        dto.setLogin(reading.getUser().getLogin());
        dto.setName(reading.getBook().getName());
        dto.setAuthor(reading.getBook().getAuthor());
        dto.setEvaluationOfCharacter(reading.getEvaluationOfCharacter());
        dto.setEvaluationOfPlot(reading.getEvaluationOfPlot());
        dto.setFinalMark(reading.getFinalMark());
        dto.setEvaluationOfEmotions(reading.getEvaluationOfEmotions());
        dto.setQualityOfDialog(reading.getQualityOfDialog());
        dto.setAtmosphere(reading.getAtmosphere());
        dto.setReview(reading.getReview());
        dto.setBookGenres(reading.getBook().getGenres()
                .stream()
                .map(Genre::getId)
                .toList());
        return dto;
    }
    public static ReadingViewerResponse toReadingViewerResponse(Reading reading) {
        ReadingViewerResponse dto = new ReadingViewerResponse();
        dto.setId(reading.getId());
        dto.setUserId(reading.getUser().getId());
        dto.setReadingStatus(reading.getStatus());
        dto.setLogin(reading.getUser().getLogin());
        dto.setBookId(reading.getBook().getId());
        dto.setAuthor(reading.getBook().getAuthor());
        dto.setName(reading.getBook().getName());
        dto.setFinalMark(reading.getFinalMark());
        dto.setBookGenres(reading.getBook().getGenres()
                .stream()
                .map(Genre::getId)
                .toList());
        return dto;
    }
}
