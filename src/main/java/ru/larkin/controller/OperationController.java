package ru.larkin.controller;

import lombok.RequiredArgsConstructor;
import ru.larkin.exception.InvalidInputException;
import ru.larkin.exception.LibraryException;
import ru.larkin.service.OperationService;

import java.util.Scanner;

@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final Scanner scanner;

    public void operationManagement() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--------------------------");
            System.out.println("         ОПЕРАЦИИ         ");
            System.out.println("--------------------------");
            String bookMenu = """
                    1. Взять книгу
                    2. Вернуть книгу
                    3. Отследить просроченные книги
                    0. Назад
                    Выберите действие:
                    """;
            System.out.println(bookMenu);
            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "0" -> {
                        System.out.println("Выход в главное меню...");
                        isRunning = false;
                    }
                    case "1" -> operationService.borrowBook();
                    case "2" -> operationService.returnBook();
                    case "3" -> operationService.getOverdueBooks();
                    default -> throw new InvalidInputException("Неверный ввод. Попробуйте еще раз.");
                }
            } catch (LibraryException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}
