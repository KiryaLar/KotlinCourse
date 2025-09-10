package ru.larkin.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Student extends User {

    @Override
    public int getMaxBooks() {
        return 3;
    }

    @Override
    public int getBorrowDays() {
        return 14;
    }

    @Override
    public double getFinePerDay() {
        return 50;
    }
}
