package com.example.bookreader.DTO.BookControllerDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String author;

    private String description;

    @NotNull
    private Boolean isPrivate;


}
