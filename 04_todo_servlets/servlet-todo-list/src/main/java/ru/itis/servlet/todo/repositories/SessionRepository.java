package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.Session;

import java.time.LocalDateTime;

public interface SessionRepository {
    void addSession(Long userId, String sessionId, LocalDateTime expireAt);
    Session getSessionById(String sessionId);
}
