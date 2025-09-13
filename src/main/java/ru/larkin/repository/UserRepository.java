package ru.larkin.repository;

import ru.larkin.model.User;
import ru.larkin.model.UserType;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void addUser(User user);
    
    Optional<User> findUserById(String userId);
    
    List<User> findAllUsers();
    
    void removeUserById(String userId);

    List<User> findUsersByCriteria(String name, String email, Integer yearFrom, Integer yearTo, UserType userType);
}
