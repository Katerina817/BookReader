package com.example.bookreader.DTO.BookControllerDTO;

import lombok.Data;

@Data
public class UpdateBookRequest {
    private String name;

    private String author;

    private String description;

    private Boolean isPrivate;

}
