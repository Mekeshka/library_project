package ru.itgirl.libraryproject.service;
import ru.itgirl.libraryproject.dto.*;

public interface BookService {
    BookDto getByNameV1(String name);
    BookDto getByNameV2(String name);
    BookDto getByNameV3(String name);
//    BookDto createBook(BookCreateDto bookCreateDto);
//    BookDto updateBook(BookUpdateDto bookUpdateDto);
//    void deleteBook(Long id);
}

