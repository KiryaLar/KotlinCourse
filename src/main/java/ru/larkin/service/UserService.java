package ru.larkin.service;

import lombok.RequiredArgsConstructor;
import ru.larkin.exception.NotFoundException;
import ru.larkin.model.*;
import ru.larkin.repository.UserRepository;
import ru.larkin.util.CreatorUtilService;
import ru.larkin.util.InputUtilService;

import java.util.*;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Scanner scanner;

    public void registerUser() {
        System.out.println("Регистрируем пользователя: ");

        String userId = UUID.randomUUID().toString().substring(0, 6);

        String name = InputUtilService.getInput("Введите имя пользователя: ",
                input -> !input.isBlank(),
                "Имя пользователя не может быть пустым");

        String email = InputUtilService.getInput("Введите email пользователя: ",
                input -> !input.isBlank(),
                "Email пользователя не может быть не указан");

        int year = Integer.parseInt(InputUtilService.getInput("Введите год рождения: ",
                input -> {
                    try {
                        int y = Integer.parseInt(input);
                        return y >= 1925 && y <= 2025;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }, "Год рождения книги должен быть в диапазоне 1925-2018"));


        UserType userType = InputUtilService.getInputUserType();

        User newUser = CreatorUtilService.createTypeUser(userId, name, email, year, userType);

        userRepository.addUser(newUser);
        System.out.printf("Пользователь с %s успешно создан.%n", userId);
    }

    public void getAllUsers() {
        System.out.println("Список всех пользователей: ");
        List<User> users = userRepository.findAllUsers();
        if (users.isEmpty()) {
            System.out.println("На данный момент пользователи отсутствуют");
        } else {
            int counter = 1;
            for (User user : users) {
                System.out.println(counter + ") " + user);
            }
        }
    }

    public void getUserById() {
        System.out.println("Поиск пользователя по ID");

        System.out.println("Введите ID пользователя: ");
        String userId = scanner.nextLine();
        Optional<User> maybeUser = userRepository.findUserById(userId);
        if (maybeUser.isPresent()) {
            System.out.println("Найденный пользователь: ");
            System.out.println(maybeUser.get());
        } else {
            throw NotFoundException.user(userId);
        }
    }

    public void removeUser() {
        System.out.println("Удаление пользователя");

        System.out.println("Введите ID пользователя: ");
        String userId = scanner.nextLine();
        userRepository.removeUserById(userId);
        System.out.printf("Пользователь с ID %s успешно удален", userId);
    }

    public void getUsersByCriteria() {
        System.out.println("Поиск пользователей по критериям");

        System.out.println("Введите критерии поиска: ");
        String name = InputUtilService.getInput("Имя (оставьте пустым для любого): ");
        String email = InputUtilService.getInput("Email (оставьте пустым для любого): ");
        Integer yearFrom = InputUtilService.parseIntInput("Год рождения от (оставьте пустым, если без ограничений): ");
        Integer yearTo = InputUtilService.parseIntInput("Год рождения до (оставьте пустым, если без ограничений): ");
        UserType userType = InputUtilService.getInputUserType();

        List<User> filteredUsers = userRepository.findUsersByCriteria(name, email, yearFrom, yearTo, userType);

        if (filteredUsers.isEmpty()) {
            System.out.println("Нет пользователей, соответствующих критериям.");
        } else {
            System.out.println("Найденные пользователи: ");
            int counter = 1;
            for (User user : filteredUsers) {
                System.out.println(counter++ + ". " + user);
            }
        }
    }


}
