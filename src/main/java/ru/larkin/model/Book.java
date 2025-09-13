package ru.larkin.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    private String id;
    private String title;
    private String author;
    private Integer year;
    private String genre;
    private BookStatus status;
    private LocalDateTime borrowedAt;

    @Override
    public String toString() {
        return id + " '" + title + '\'' +
               ", автор='" + author + '\'' +
               ", год выпуска=" + year +
               ", жанр='" + genre + '\'';
    }
}