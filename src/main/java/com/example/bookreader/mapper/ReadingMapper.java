package com.example.bookreader.mapper;

import com.example.bookreader.DTO.ReadingControllerDTO.ReadingResponse;
import com.example.bookreader.entity.Reading;
import java.util.stream.Collectors;

public class ReadingMapper {
    public static ReadingResponse toReadingResponse(Reading reading) {
        ReadingResponse dto = new ReadingResponse();
        dto.setUuid(reading.getId());
        dto.setReadingStatus(reading.getStatus());
        dto.setDateStartOfReading(reading.getDateStartOfReading());
        dto.setDateFinishOfReading(reading.getDateFinishOfReading());
        dto.setUserId(reading.getUser().getId());
        dto.setBookId(reading.getBook().getId());
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
        return dto;
    }
}
