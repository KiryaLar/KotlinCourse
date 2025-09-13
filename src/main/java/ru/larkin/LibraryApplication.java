package ru.larkin;

import ru.larkin.controller.BookController;
import ru.larkin.controller.MainMenuController;
import ru.larkin.controller.OperationController;
import ru.larkin.controller.UserController;
import ru.larkin.repository.BookRepository;
import ru.larkin.repository.UserRepository;
import ru.larkin.repository.impl.InMemoryBookRepository;
import ru.larkin.repository.impl.InMemoryUserRepository;
import ru.larkin.service.BookService;
import ru.larkin.service.OperationService;
import ru.larkin.service.UserService;

import java.util.Scanner;

public class LibraryApplication {

    private final UserController userController;
    private final BookController bookController;
    private final OperationController operationController;

    public LibraryApplication() {

        UserRepository userRepository = new InMemoryUserRepository();
        BookRepository bookRepository = new InMemoryBookRepository();

        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(userRepository, scanner);
        BookService bookService = new BookService(bookRepository, scanner);
        OperationService operationService = new OperationService(userRepository, bookRepository, scanner);

        this.userController = new UserController(userService, scanner);
        this.bookController = new BookController(bookService, scanner);
        this.operationController = new OperationController(operationService, scanner);
    }

    public static void main(String[] args) {
        LibraryApplication app = new LibraryApplication();
        app.startApp();
    }

    private void startApp() {
        MainMenuController mainMenuController = new MainMenuController(userController, bookController, operationController);
        mainMenuController.run();
    }
}