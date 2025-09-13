package ru.larkin.repository.impl;

import ru.larkin.exception.AlreadyExistsException;
import ru.larkin.exception.NotFoundException;
import ru.larkin.model.User;
import ru.larkin.model.UserType;
import ru.larkin.repository.UserRepository;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {

    private final HashMap<String, User> users;
    private final HashSet<String> uniqueEmails;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
        this.uniqueEmails = new HashSet<>();
    }

    @Override
    public void addUser(User user) {
        String userId = user.getUserId();
        String email = user.getEmail();
        if (uniqueEmails.contains(email)) {
            throw AlreadyExistsException.user(email);
        } else {
            uniqueEmails.add(email);
            users.put(userId, user);
        }
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void removeUserById(String userId) {
        if (users.containsKey(userId)) {
            User deletedUser = users.remove(userId);
            uniqueEmails.remove(deletedUser.getEmail());
        } else {
            throw NotFoundException.user(userId);
        } 
    }

    @Override
    public List<User> findUsersByCriteria(String name, String email, Integer yearFrom, Integer yearTo, UserType userType) {
        return users.values().stream()
                .filter(user -> name.isBlank() || user.getName().equalsIgnoreCase(name))
                .filter(user -> email.isBlank() || user.getEmail().equalsIgnoreCase(email))
                .filter(user -> yearFrom == null || user.getBirthYear() >= yearFrom)
                .filter(user -> yearTo == null || user.getBirthYear() <= yearTo)
                .filter(user -> userType == null || user.getUserType() == userType)
                .toList();
    }


}
