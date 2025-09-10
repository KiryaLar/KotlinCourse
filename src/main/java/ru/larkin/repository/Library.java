package ru.larkin.repository;

import ru.larkin.model.Book;
import ru.larkin.model.BorrowingRecord;
import ru.larkin.model.User;
import ru.larkin.model.UserType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Library implements BookRepository, UserRepository {

    private Map<String, Book> books;
    private Map<String, User> users;
    private List<BorrowingRecord> borrowingRecords;
    private Double bookFine;
//    ???
//    private Set<String> genres;

    @Override
    public void addBook(Book book) {

    }

    @Override
    public boolean removeBookById(String bookId) {
        if (books.containsKey(bookId)) {
            books.remove(bookId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Book> findBookById(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    @Override
    public List<Book> searchBooks(String query) {
        return books.values().stream()
                .filter(book -> book.getTitle().contains(query))
                .toList();
    }

    @Override
    public void addUser(String userId, String name, String email, Integer birthYear, UserType type) {
        User user = User.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthYear(birthYear)
                .userType(type)
                .build();
        users.put(userId, user);
    }

    @Override
    public Optional<User> findUser(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public boolean borrowBook(String userId, String bookId) {
        return false;
    }

    @Override
    public boolean returnBook(String userId, String bookId) {
        return false;
    }

    @Override
    public List<BorrowingRecord> getOverdueBooks() {
        return List.of();
    }
}
