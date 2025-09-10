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
    protected List<Book> borrowedBooks = new ArrayList<>();

    public abstract int getMaxBooks();
    public abstract int getBorrowDays();
    public abstract double getFinePerDay();

//    public int getBooksNum() {
//        return borrowedBooks.size();
//    }

    public boolean canBorrow() {
        return borrowedBooks.size() < getMaxBooks();
    }
}
