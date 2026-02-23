package com.example.bookreader.DTO.ReadingControllerDTO.Response;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReadingFriendResponse extends BaseReadingResponse {
    private LocalDateTime dateStartOfReading;
    private LocalDateTime dateFinishOfReading;
    private Integer evaluationOfCharacter;
    private Integer evaluationOfPlot;
    private Integer evaluationOfEmotions;
    private Integer qualityOfDialog;
    private Integer atmosphere;
    private String review;
}

