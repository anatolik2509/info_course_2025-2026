package ru.itis.servlet.todo.services;

import ru.itis.servlet.todo.models.User;

public interface SecurityService {
    String registerUser(String email, String password, String passwordRepeat);
    String loginUser(String email, String password);
    User getUser(String sessionId);
}
