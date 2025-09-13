package ru.larkin.service;

import ru.larkin.exception.NotFoundException;
import ru.larkin.model.Book;
import ru.larkin.model.BookStatus;
import ru.larkin.model.User;
import ru.larkin.repository.BookRepository;
import ru.larkin.repository.UserRepository;
import ru.larkin.util.InputUtilService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OperationService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final Scanner scanner;

    public OperationService(UserRepository userRepository, BookRepository bookRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.scanner = scanner;
    }

    public void borrowBook() {
        System.out.println("Выбор книги");

        User user = chooseUser();
        System.out.printf("Пользователь " + user + " собирается выбрать книгу");
        List<Book> books = bookRepository.getAvailableBooks();

        if (books.isEmpty()) {
            System.out.println("На данный момент доступных книг нет");
        } else {
            System.out.println("Доступные книги: ");
            int counter = 1;
            for (Book book : books) {
                System.out.println(counter++ + ") " + book);
            }

            System.out.println("Выберите книгу из списка (Введите порядковый номер): ");
            int choice = InputUtilService.getIntInputWithLimit(books.size());
            Book book = books.get(choice - 1);
            book.setBorrowedAt(LocalDateTime.now());
            book.setStatus(BookStatus.BORROWED);

            user.addBorrowedBook(book);
            System.out.println("Пользователь " + user.getName() + " успешно взял книгу.");
            System.out.println("Книгу необходимо вернуть в течении " + user.getBorrowDays() + " дней.");
            System.out.println("Штраф за каждый просроченный день: " + user.getFinePerDay());
        }
    }

    public void returnBook() {
        System.out.println("Возврат книги");

        User user = chooseUser();
        System.out.printf("Пользователь " + user + " собирается вернуть книгу");
        List<Book> borrowedBooks = user.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("У пользователя " + user + " нет взятых книг.");
        } else {
            int counter = 1;
            for (Book book : borrowedBooks) {
                System.out.println(counter++ + ") " + book);
            }

            System.out.println("Выберите книгу, которую хотите вернуть: ");
            int choice = InputUtilService.getIntInputWithLimit(borrowedBooks.size());
            Book book = borrowedBooks.get(choice - 1);

            LocalDateTime borrowDate = book.getBorrowedAt();
            int availableBorrowDays = user.getBorrowDays();
            LocalDateTime dueDate = borrowDate.plusDays(availableBorrowDays);

            if (LocalDateTime.now().isAfter(dueDate)) {
                long overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDateTime.now());
                System.out.println("Вы просрочили сдачу книги на " + overdueDays + " дней");
                BigDecimal fine = BigDecimal.valueOf(user.getFinePerDay())
                        .multiply(BigDecimal.valueOf(overdueDays));
                System.out.println("Вы должны заплатить штраф в размере: " + fine);
            } else {
                System.out.println("Вы сдаете книгу вовремя");
            }

            book.setStatus(BookStatus.AVAILABLE);
            book.setBorrowedAt(null);
            user.removeBook(book);

            System.out.println("Пользователь " + user.getName() + " успешно вернул книгу.");
        }
    }

    public void getOverdueBooks() {
        System.out.println("Проверка на просроченные даты сдачи книг");

        User user = chooseUser();
        int availableBorrowDays = user.getBorrowDays();

        List<Book> overdueBooks = user.getBorrowedBooks().stream()
                .filter(book -> {
                    LocalDateTime borrowDate = book.getBorrowedAt();
                    LocalDateTime dueDate = borrowDate.plusDays(availableBorrowDays);
                    return LocalDateTime.now().isAfter(dueDate);
                })
                .toList();

        if (overdueBooks.isEmpty()) {
            System.out.println("У пользователя " + user + " нет книг с просроченной датой сдачи.");
        } else {
            System.out.println("Книги, которые просрочил пользователь " + user + ": ");
            int counter = 1;
            for (Book book : overdueBooks) {
                System.out.println(counter++ + ") " + book);
                printOverdueInfo(user, book);
            }
        }
    }

    private User chooseUser() {
        System.out.println("Введите ID пользователя: ");
        String userId = scanner.nextLine();
        Optional<User> maybeUser = userRepository.findUserById(userId);
        if (maybeUser.isEmpty()) {
            throw NotFoundException.user(userId);
        }
        return maybeUser.get();
    }

    private void printOverdueInfo(User user, Book book) {
        LocalDateTime borrowDate = book.getBorrowedAt();
        int availableBorrowDays = user.getBorrowDays();
        LocalDateTime dueDate = borrowDate.plusDays(availableBorrowDays);

        long overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDateTime.now());
        System.out.println("Сдача книги просрочена на " + overdueDays + " дней");
        BigDecimal fine = BigDecimal.valueOf(user.getFinePerDay())
                .multiply(BigDecimal.valueOf(overdueDays));
        System.out.println("Штраф: " + fine);
    }
}
