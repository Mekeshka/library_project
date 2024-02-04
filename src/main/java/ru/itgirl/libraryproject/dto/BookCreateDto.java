package ru.itgirl.libraryproject.dto;
import lombok.*;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    private String name;
    private Long genreID;
}


