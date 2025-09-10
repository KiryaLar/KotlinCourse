package ru.larkin.service;

import lombok.RequiredArgsConstructor;
import ru.larkin.model.*;
import ru.larkin.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


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
                        return y >= 1000 && y <= 2025;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }, "Год рождения книги должен быть в диапазоне 1925-2018"));


        UserType userType = getInputUserType();

        User newUser = createTypeUser(userId, name, email, year, userType);



        userRepository.addUser(newUser);
        System.out.printf("Пользователь с %s успешно создан.%n", userId);
    }

    public void getAllUsers() {
    }

    public void findUsersByCriteria() {
    }

    public void removeUser() {
    }

    private UserType getInputUserType() {
        String type = InputUtilService.getInput("Введите тип пользователя (STUDENT, FACULTY, GUEST): ",
                input -> {
                    return input.equalsIgnoreCase("STUDENT") ||
                           input.equalsIgnoreCase("FACULTY") ||
                           input.equalsIgnoreCase("GUEST");
                },
                "Неправильный ввод типа пользователя, возможные варианты: STUDENT, FACULTY, GUEST");
        return UserType.valueOf(type.toUpperCase());
    }

    private User createTypeUser(String userId, String name, String email, int year, UserType userType) {
        switch (userType) {
            case STUDENT -> {
                return createStudent(userId, name, email, year);
            }
            case FACULTY -> {
                return createFaculty(userId, name, email, year);
            }
            case GUEST -> {
                return createGuest(userId, name, email, year);
            }
            default -> throw new NoSuchElementException("Типа пользователя %s не существует".formatted(userType));
        }
    }

    private Guest createGuest(String userId, String name, String email, int year) {
        return Guest.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthYear(year)
                .userType(UserType.GUEST)
                .build();
    }

    private Faculty createFaculty(String userId, String name, String email, int year) {
        return Faculty.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthYear(year)
                .userType(UserType.FACULTY)
                .build();
    }

    private Student createStudent(String userId, String name, String email, int year) {
        return Student.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthYear(year)
                .userType(UserType.STUDENT)
                .build();
    }
}
