package com.example.bookreader.DTO.ReadingControllerDTO;


import com.example.bookreader.DTO.NoteControllerDTO.NoteResponse;
import com.example.bookreader.entity.ReadingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
public class ReadingResponse {
    private UUID id;
    private ReadingStatus readingStatus;
    private LocalDateTime dateStartOfReading;
    private LocalDateTime dateFinishOfReading;
    private UUID userId;
    private UUID bookId;
    private Integer evaluationOfCharacter;
    private Integer evaluationOfPlot;
    private Integer evaluationOfEmotions;
    private Integer qualityOfDialog;
    private Integer atmosphere;
    private String review;
    private List<NoteResponse> notes;
}
