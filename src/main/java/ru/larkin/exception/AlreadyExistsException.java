package ru.larkin.exception;

public class AlreadyExistsException extends LibraryException {

    private AlreadyExistsException(String message) {
        super(message);
    }

    public static AlreadyExistsException book(String id) {
        return new AlreadyExistsException("Book with id " + id + " already exists");
    }

    public static AlreadyExistsException user(String email) {
        return new AlreadyExistsException("User with email " + email + " already exists");
    }
}
