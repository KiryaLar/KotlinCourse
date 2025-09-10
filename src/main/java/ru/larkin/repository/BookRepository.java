package ru.larkin.repository;

import ru.larkin.model.Book;
import ru.larkin.model.BookStatus;
import ru.larkin.model.BorrowingRecord;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void addBook(Book book);
    void removeBookById(String bookId);
    Optional<Book> findBookById(String bookId);
    List<Book> getAllBooks();
    List<Book> findBooksByCriteria(String title, String author, String genre, Integer yearFrom, Integer yearTo, BookStatus status);

    boolean borrowBook(String userId, String bookId);
    boolean returnBook(String userId, String bookId);
    List<BorrowingRecord> getOverdueBooks();
}
