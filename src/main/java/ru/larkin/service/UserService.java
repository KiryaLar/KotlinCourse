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

//    private static final int MIN_BIRTH_YEAR = 1900;
//    private static final int MAX_BIRTH_YEAR = Year.now().getValue();
//
//    private final UserRepository userRepository;
//
//    public UserRegistrationService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public void registerUser() {
//        System.out.println("Регистрируем пользователя: ");
//
//        String userId = UUID.randomUUID().toString().substring(0, 6);
//
//        String name  = prompt("Введите имя пользователя: ",
//                s -> !s.isBlank(),
//                "Имя пользователя не может быть пустым");
//
//        String email = prompt("Введите email пользователя: ",
//                s -> !s.isBlank() && isEmail(s),
//                "Укажите корректный email (например, user@example.com)");
//
//        int year     = promptInt("Введите год рождения: ",
//                y -> y >= MIN_BIRTH_YEAR && y <= MAX_BIRTH_YEAR,
//                "Год рождения должен быть в диапазоне %d–%d".formatted(MIN_BIRTH_YEAR, MAX_BIRTH_YEAR));
//
//        UserType userType = promptEnum(
//                "Введите тип пользователя (STUDENT, FACULTY, GUEST): ",
//                UserType.class,
//                "Неверный тип. Возможные варианты: STUDENT, FACULTY, GUEST");
//
//        UserData data = new UserData(userId, name, email, year, userType);
//
//        User newUser = FACTORY.getOrDefault(userType, __ -> {
//            throw new NoSuchElementException("Тип пользователя %s не поддержан".formatted(userType));
//        }).apply(data);
//
//        userRepository.addUser(newUser);
//        System.out.printf("Пользователь с id %s успешно создан.%n", userId);
//    }
//
//    // ---- Фабрика по типу пользователя ----
//
//    private static final Map<UserType, Function<UserData, User>> FACTORY = Map.of(
//        UserType.STUDENT, d -> Student.builder()
//                .userId(d.userId()).name(d.name()).email(d.email())
//                .birthYear(d.birthYear()).userType(UserType.STUDENT).build(),
//        UserType.FACULTY, d -> Faculty.builder()
//                .userId(d.userId()).name(d.name()).email(d.email())
//                .birthYear(d.birthYear()).userType(UserType.FACULTY).build(),
//        UserType.GUEST,   d -> Guest.builder()
//                .userId(d.userId()).name(d.name()).email(d.email())
//                .birthYear(d.birthYear()).userType(UserType.GUEST).build()
//    );
//
//    // ---- Хелперы ввода ----
//
//    private static String prompt(String label,
//                                 java.util.function.Predicate<String> ok,
//                                 String errMsg) {
//        return InputUtilService.getInput(label, ok, errMsg);
//    }
//
//    private static int promptInt(String label,
//                                 java.util.function.IntPredicate ok,
//                                 String errMsg) {
//        return Integer.parseInt(InputUtilService.getInput(label, s -> {
//            try {
//                int v = Integer.parseInt(s);
//                return ok.test(v);
//            } catch (NumberFormatException e) {
//                return false;
//            }
//        }, errMsg));
//    }
//
//    private static <E extends Enum<E>> E promptEnum(String label,
//                                                    Class<E> enumType,
//                                                    String errMsg) {
//        String raw = InputUtilService.getInput(label, s -> {
//            try { Enum.valueOf(enumType, s.trim().toUpperCase()); return true; }
//            catch (IllegalArgumentException e) { return false; }
//        }, errMsg);
//        return Enum.valueOf(enumType, raw.trim().toUpperCase());
//    }
//
//    private static boolean isEmail(String s) {
//        // Простая проверка. Если нужен строгий формат — используй Apache Commons Validator.
//        return s.contains("@") && s.indexOf('@') > 0 && s.indexOf('@') < s.length() - 1;
//    }
//
//    // переносим «общий набор полей» в компактный контейнер
//    private record UserData(String userId, String name, String email, int birthYear, UserType type) {}
//}
//Что улучшили
//❌ Убрали три почти одинаковых метода createStudent/Faculty/Guest.
//
//✅ Единый фабричный Map — легко добавить новый UserType.
//
//✅ Единые, переиспользуемые хелперы для ввода чисел и enum.
//
//✅ Корректные границы года (автоматически учитывают текущий год).
//
//✅ Консистентные тексты ошибок.
//
//🧩 Если используешь Lombok @SuperBuilder в иерархии User → Student/Faculty/Guest, этот код продолжит работать без изменений.
//
//Микро-совет: вместо UUID.substring(0,6) лучше использовать короткий, но читабельный генератор (например, NanoID) — меньше коллизий при массовом создании.
}
