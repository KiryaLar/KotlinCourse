package ru.larkin.repository;

import ru.larkin.model.Book;
import ru.larkin.model.BookStatus;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void addBook(Book book);

    Optional<Book> findBookById(String bookId);

    List<Book> getAllBooks();

    List<Book> getAvailableBooks();

    List<Book> findBooksByCriteria(String title, String author, String genre, Integer yearFrom, Integer yearTo, BookStatus status);

    void removeBookById(String bookId);
}
