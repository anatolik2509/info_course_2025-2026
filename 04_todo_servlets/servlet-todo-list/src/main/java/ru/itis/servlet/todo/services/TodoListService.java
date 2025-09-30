package ru.itis.servlet.todo.services;

import ru.itis.servlet.todo.models.Task;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface TodoListService {
    void addTask(@Nullable String taskText);

    @Nonnull
    List<Task> getTasks();

    void removeTask(@Nullable Long taskId);
}
