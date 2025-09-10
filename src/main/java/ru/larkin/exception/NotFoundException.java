package ru.larkin.exception;

public class NotFoundException extends LibraryException{

    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException book(String id) {
        return new NotFoundException("Book with id " + id + " not found");
    }


}
