package ru.larkin.util;

import lombok.experimental.UtilityClass;
import ru.larkin.model.*;

import java.util.NoSuchElementException;

@UtilityClass
public class CreatorUtilService {

    public static User createTypeUser(String userId, String name, String email, int year, UserType userType) {
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
