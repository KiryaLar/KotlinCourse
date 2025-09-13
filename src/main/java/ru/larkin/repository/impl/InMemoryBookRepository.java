package ru.larkin.repository.impl;

import ru.larkin.exception.AlreadyExistsException;
import ru.larkin.exception.NotFoundException;
import ru.larkin.model.Book;
import ru.larkin.model.BookStatus;
import ru.larkin.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {

    private final HashMap<String, Book> bookStorage;

    public InMemoryBookRepository() {
        this.bookStorage = new HashMap<>();
    }

    @Override
    public void addBook(Book book) {
        String bookId = book.getId();
        if (bookStorage.containsKey(bookId)) {
            throw AlreadyExistsException.book(bookId);
        } else {
            bookStorage.put(bookId, book);
        }
    }

    @Override
    public void removeBookById(String bookId) {
        if (bookStorage.containsKey(bookId)) {
            bookStorage.remove(bookId);
        } else {
            throw NotFoundException.book(bookId);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookStorage.values());
    }

    @Override
    public List<Book> getAvailableBooks() {
        return bookStorage.values().stream()
                .filter(book -> book.getStatus().equals(BookStatus.AVAILABLE))
                .toList();
    }

    @Override
    public Optional<Book> findBookById(String bookId) {
        return Optional.ofNullable(bookStorage.get(bookId));
    }

    @Override
    public List<Book> findBooksByCriteria(String title, String author, String genre, Integer yearFrom, Integer yearTo, BookStatus status) {
        return bookStorage.values().stream()
                .filter(book -> title.isBlank() || book.getTitle().equalsIgnoreCase(title))
                .filter(book -> author.isBlank() || book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> genre.isBlank() || book.getGenre().equalsIgnoreCase(author))
                .filter(book -> yearFrom == null || book.getYear() >= yearFrom)
                .filter(book -> yearTo == null || book.getYear() <= yearTo)
                .filter(book -> status == null || book.getStatus() == status)
                .toList();
    }
}
