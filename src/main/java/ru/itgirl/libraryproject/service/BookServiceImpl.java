package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.*;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        return convertEntityToDto(book);
    }
    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(book);
    }
    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
//    @Override
//    public BookDto createBook(BookCreateDto bookCreateDto) {
//        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
//        BookDto bookDto = convertEntityToDto(book);
//        return bookDto;
//    }
//
//    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
//        return Book.builder()
//                .name(bookCreateDto.getName())
//                .genre(bookCreateDto.getGenre())
//                .build();
//    }
//
//    private BookDto convertEntityToDto(Book book) {
//        List<BookDto> bookDtoList = null;
//        if (book.getAuthors() != null) {
//            bookDtoList = book.getAuthors()
//                    .stream()
//                    .map(book -> BookDto.builder()
//                            .genre(book.getGenre().getName())
//                            .name(book.getName())
//                            .id(book.getId())
//                            .build())
//                    .toList();
//        }
//
//        BookDto bookDto = BookDto.builder()
//                .id(book.getId())
//                .name(book.getName())
//                .genre(book.getGenre())
//                .build();
//        return bookDto;
//    }
//    @Override
//    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
//        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
//        book.setName(bookUpdateDto.getName());
//        book.setGenre(bookUpdateDto.getGenre());
//        Book savedBook = bookRepository.save(book);
//        BookDto bookDto = convertEntityToDto(savedBook);
//        return bookDto;
//    }
//    @Override
//    public void deleteBook(Long id) {
//        bookRepository.deleteById(id);
//    }
}
