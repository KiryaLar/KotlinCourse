package ru.larkin.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected int birthYear;
    protected UserType userType;
    @Builder.Default
    @Setter(AccessLevel.NONE)
    protected List<Book> borrowedBooks = new ArrayList<>();

    public abstract int getMaxBooks();
    public abstract int getBorrowDays();
    public abstract double getFinePerDay();

    public boolean canBorrow() {
        return borrowedBooks.size() < getMaxBooks();
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return userId + " " + name +
               ", email='" + email + '\'' +
               ", год рождения=" + birthYear +
               " (" + userType + ") ";
    }
}
