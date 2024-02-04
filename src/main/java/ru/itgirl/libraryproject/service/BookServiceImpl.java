package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.BookCreateDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.dto.BookUpdateDto;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.model.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        log.info("Try to find book by name {}", name);
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Creating book");
        Genre genre = genreRepository.findById(bookCreateDto.getGenreID()).orElseThrow();
        Book book = convertDtoToEntity(bookCreateDto);
        book.setGenre(genre);
        book = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Updating book");
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        if (!book.getGenre().getId().equals(bookUpdateDto.getGenreID())) {
            Genre genre = genreRepository.findById(bookUpdateDto.getGenreID()).orElseThrow();
            book.setGenre(genre);
        }
        ;
        book = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        return Book.builder()
                .name(bookCreateDto.getName())
                .build();
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book by id {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Listing all books");
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
}
