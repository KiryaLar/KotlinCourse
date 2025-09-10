package ru.larkin.service;

import lombok.experimental.UtilityClass;
import ru.larkin.model.BookStatus;

import java.util.Scanner;
import java.util.function.Predicate;

@UtilityClass
public class InputUtilService {

    private final Scanner scanner = new Scanner(System.in);

    public static String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public static Integer parseIntInput(String prompt) {
        String input = getInput(prompt);
        try {
            return input.isEmpty() ? null : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Будет использовано без ограничений.");
            return null;
        }
    }

    public static BookStatus parseStatusInput() {
        String input = getInput("Статус [AVAILABLE/BORROWED] (оставьте пустым для любого): ");
        try {
            return input.isEmpty() ? null : BookStatus.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный статус. Будет использовано без ограничений.");
            return null;
        }
    }

    public static String getInput(String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (validator.test(input)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }
}
