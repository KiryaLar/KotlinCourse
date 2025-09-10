package ru.larkin.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Guest extends User {
    @Override
    public int getMaxBooks() {
        return 1;
    }

    @Override
    public int getBorrowDays() {
        return 7;
    }

    @Override
    public double getFinePerDay() {
        return 200;
    }
}
