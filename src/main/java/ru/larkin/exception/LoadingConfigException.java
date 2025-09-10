package ru.larkin.exception;

public class LoadingConfigException extends LibraryException {
    public LoadingConfigException(Throwable cause) {
        super("Failed to load config file", cause);
    }
}
