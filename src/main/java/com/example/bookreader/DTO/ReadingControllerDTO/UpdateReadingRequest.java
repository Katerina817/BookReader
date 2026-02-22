package com.example.bookreader.DTO.ReadingControllerDTO;

import com.example.bookreader.enums.ReadingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateReadingRequest {
    private ReadingStatus readingStatus;
    private LocalDateTime dateStartOfReading;
    private LocalDateTime dateFinishOfReading;
    private Integer evaluationOfCharacter;
    private Integer evaluationOfPlot;
    private Integer evaluationOfEmotions;
    private Integer qualityOfDialog;
    private Integer atmosphere;
    private String review;
    private Boolean privateReading;
}
