package ru.larkin.service;

import lombok.RequiredArgsConstructor;
import ru.larkin.exception.NotFoundException;
import ru.larkin.model.Book;
import ru.larkin.model.BookStatus;
import ru.larkin.repository.BookRepository;
import ru.larkin.util.InputUtilService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final Scanner scanner;

    public void addBook() {
        System.out.println("Добавляем книгу: ");

        String bookId = InputUtilService.getInput("Введите id книги: ",
                input -> !input.isEmpty() && input.length() <= 10 && input.matches("^[a-zA-Z0-9]+$"),
                "id должен содержать не более 10 символов и состоять только из букв и цифр");

        String title = InputUtilService.getInput("Введите название книги: ",
                input -> !input.isBlank(),
                "Название книги не может быть пустым");

        String author = InputUtilService.getInput("Введите автора книги: ",
                input -> !input.isBlank(),
                "Автор книги не может быть не указан");

        String genre = InputUtilService.getInput("Введите жанр книги: ",
                input -> !input.isBlank(),
                "Жанр книги не может быть пустым");

        int year = Integer.parseInt(InputUtilService.getInput("Введите год выпуска книги: ", input -> {
            try {
                int y = Integer.parseInt(input);
                return y >= 1000 && y <= 2025;
            } catch (NumberFormatException e) {
                return false;
            }
        }, "Год выпуска книги должен быть в диапазоне 1000-2025"));

        Book book = Book.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .year(year)
                .genre(genre)
                .status(BookStatus.AVAILABLE)
                .build();

        bookRepository.addBook(book);
        System.out.println("Книга успешно добавлена. (Статус: Доступна)\n");
    }

    public void getAllBooks() {
        System.out.println("Список всех книг: ");
        List<Book> books = bookRepository.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("На данный момент книги отсутствуют");
        } else {
            int counter = 1;
            for (Book book : books) {
                System.out.println(counter++ + ") " + book);
            }
        }
    }

    public void getBookById() {
        System.out.println("Поиск книги по ID");

        System.out.println("Введите ID книги: ");
        String bookId = scanner.nextLine();
        Optional<Book> maybeBook = bookRepository.findBookById(bookId);
        if (maybeBook.isPresent()) {
            System.out.println("Найденная книга: ");
            System.out.println(maybeBook.get());
        } else {
            throw  NotFoundException.book(bookId);
        }
    }

    public void getBooksByCriteria() {
        System.out.println("Поиск книг по критериям");

        System.out.println("Введите критерии поиска: ");
        String title = InputUtilService.getInput("Название (оставьте пустым для любого): ");
        String author = InputUtilService.getInput("Автор (оставьте пустым для любого): ");
        String genre = InputUtilService.getInput("Жанр (оставьте пустым для любого): ");
        Integer yearFrom = InputUtilService.parseIntInput("Год от (оставьте пустым, если без ограничений): ");
        Integer yearTo = InputUtilService.parseIntInput("Год до (оставьте пустым, если без ограничений): ");
        BookStatus status = InputUtilService.parseStatusInput();

        List<Book> filteredBooks = bookRepository.findBooksByCriteria(title, author, genre, yearFrom, yearTo, status);

        if (filteredBooks.isEmpty()) {
            System.out.println("Нет книг, соответствующих критериям.");
        } else {
            System.out.println("Найденные книги: ");
            int counter = 1;
            for (Book book : filteredBooks) {
                System.out.println(counter++ + ". " + book);
            }
        }
    }

    public void removeBook() {
        System.out.println("Удаление книги");

        System.out.println("Введите ID книги: ");
        String bookId = scanner.nextLine();
        bookRepository.removeBookById(bookId);
        System.out.printf("Книга с ID %s успешно удалена", bookId);
    }

    
}
