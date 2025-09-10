package ru.larkin.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Book {

    private String id;
    private String title;
    private String author;
    private Integer year;
    private String genre;
    private BookStatus status;
    private LocalDateTime borrowedAt;
}