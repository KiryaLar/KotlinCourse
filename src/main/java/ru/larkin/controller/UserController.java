package ru.larkin.controller;

import lombok.RequiredArgsConstructor;
import ru.larkin.exception.InvalidInputException;
import ru.larkin.exception.LibraryException;
import ru.larkin.service.UserService;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    public void userManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--------------------------");
            System.out.println("       ПОЛЬЗОВАТЕЛИ       ");
            System.out.println("--------------------------");
            String userMenu = """
                    1. Добавить пользователя
                    2. Список всех пользователей
                    3. Поиск по критериям
                    0. Назад
                    Выберите действие:
                    """;
            System.out.println(userMenu);
            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "0" -> {
                        System.out.println("Выход в главное меню...");
                        isRunning = false;
                    }
                    case "1" -> userService.registerUser();
                    case "2" -> userService.getAllUsers();
                    case "3" -> userService.findUsersByCriteria();
                    case "4" -> userService.removeUser();
                    default -> throw new InvalidInputException("Неверный ввод. Попробуйте еще раз.");
                }
            } catch (LibraryException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}
