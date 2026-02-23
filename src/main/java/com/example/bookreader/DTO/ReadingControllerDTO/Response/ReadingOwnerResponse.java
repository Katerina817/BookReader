package com.example.bookreader.DTO.ReadingControllerDTO.Response;


import com.example.bookreader.DTO.NoteControllerDTO.Response.NoteResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ReadingOwnerResponse extends BaseReadingResponse {
    private LocalDateTime dateStartOfReading;
    private LocalDateTime dateFinishOfReading;
    private Integer evaluationOfCharacter;
    private Integer evaluationOfPlot;
    private Integer evaluationOfEmotions;
    private Integer qualityOfDialog;
    private Integer atmosphere;
    private String review;

    private Boolean privateReading;
    private List<NoteResponse> notes;
}

