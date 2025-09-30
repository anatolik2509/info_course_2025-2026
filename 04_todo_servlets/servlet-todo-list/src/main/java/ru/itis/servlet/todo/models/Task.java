package ru.itis.servlet.todo.models;

import java.time.LocalDateTime;

public class Task {
    private final Long id;
    private final String taskText;
    private final LocalDateTime creationDate;

    public Task(Long id, String taskText, LocalDateTime creationDate) {
        this.id = id;
        this.taskText = taskText;
        this.creationDate = creationDate;
    }

    public String getTaskText() {
        return taskText;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }
}
