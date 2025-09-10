package ru.larkin.controller;

import lombok.extern.slf4j.Slf4j;
import ru.larkin.exception.InvalidInputException;

import java.util.Scanner;

public class MainMenuController {

    private final UserController userController;
    private final BookController bookController;
    private final OperationController operationController;

    public MainMenuController(UserController userController, BookController bookController, OperationController operationController) {
        this.userController = userController;
        this.bookController = bookController;
        this.operationController = operationController;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--------------------------");
            System.out.println("        БИБЛИОТЕКА        ");
            System.out.println("--------------------------");
            String mainMenu = """
                    Главное меню
                    1. Управление книгами
                    2. Управление пользователями
                    3. Просмотр операций
                    0. Выход
                    Выберите действие:
                    """;
            System.out.println(mainMenu);

            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "0" -> {
                        System.out.println("Выход...");
                        isRunning = false;
                    }
                    case "1" -> bookController.bookManagement();
                    case "2" -> userController.userManagement();
                    case "3" -> operationController.operationManagement();
                    default -> throw new InvalidInputException("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }
}
