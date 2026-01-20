package com.example.bookreader.DTO.ReadingControllerDTO;

import com.example.bookreader.entity.ReadingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class CreateReadingRequest {
    //кароч там будет две кнопки: начать читать и запланировать
    //и в зависимости от этого будет одно из состояний PLAN или READING
    @NotNull
    private ReadingStatus readingStatus;
    //если не задать вручную и статус будет READING
    //то выставляется текущее время, в ином случае либо пользователь
    //сам вводит дату, либо она вообще скрыта, если статус не READING
    private LocalDateTime dateStartOfReading;
    @NotNull
    private UUID userId;
    @NotNull
    private UUID bookId;
}
