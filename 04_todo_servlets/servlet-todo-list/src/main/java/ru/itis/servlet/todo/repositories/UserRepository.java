package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.User;

public interface UserRepository {
    Long createUser(User user);
    User getUserByLogin(String login);
    User getUserById(Long id);
}
