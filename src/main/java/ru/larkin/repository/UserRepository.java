package ru.larkin.repository;

import ru.larkin.model.User;
import ru.larkin.model.UserType;

import java.util.Optional;

public interface UserRepository {

    void addUser(User user);
    Optional<User> findUser(String userId);
}
