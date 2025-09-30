package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.Task;

import javax.annotation.Nonnull;
import java.util.List;

public interface TaskRepository {
    void addTask(@Nonnull Task task);

    @Nonnull
    List<Task> getTasks();

    void removeTask(@Nonnull Long taskId);
}
