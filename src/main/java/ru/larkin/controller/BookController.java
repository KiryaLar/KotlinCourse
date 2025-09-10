package ru.larkin.controller;

import ru.larkin.exception.InvalidInputException;
import ru.larkin.exception.LibraryException;
import ru.larkin.service.BookService;

import java.util.Scanner;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void bookManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--------------------------");
            System.out.println("          КНИГИ           ");
            System.out.println("--------------------------");
            String bookMenu = """
                    1. Добавить книгу
                    2. Список всех книг
                    3. Поиск по критериям
                    4. Удалить книгу
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
                    case "1" -> bookService.addBook();
                    case "2" -> bookService.getAllBooks();
                    case "3" -> bookService.findBooksByCriteria();
                    case "4" -> bookService.removeBook();
                    default -> throw new InvalidInputException("Неверный ввод. Попробуйте еще раз.");
                }
            } catch (LibraryException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}
