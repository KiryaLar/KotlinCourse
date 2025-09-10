package ru.larkin.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Faculty extends User {

    @Override
    public int getMaxBooks() {
        return 10;
    }

    @Override
    public int getBorrowDays() {
        return 30;
    }

    @Override
    public double getFinePerDay() {
        return 100;
    }
}
