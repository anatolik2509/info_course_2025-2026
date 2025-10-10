package ru.itis.servlet.todo.models;

import java.time.LocalDateTime;

public class Session {
    private String sessionId;
    private Long userId;
    private LocalDateTime expireAt;

    public Session(String sessionId, Long userId, LocalDateTime expireAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.expireAt = expireAt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }
}
