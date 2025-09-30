package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.Task;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> tasksById;
    private Long nextId;

    public InMemoryTaskRepository() {
        this.nextId = 1L;
        this.tasksById = new HashMap<>();
    }

    @Override
    public void addTask(@Nonnull Task task) {
        Task taskWithId = new Task(nextId++, task.getTaskText(), task.getCreationDate());
        tasksById.put(taskWithId.getId(), taskWithId);
    }

    @Nonnull
    @Override
    public List<Task> getTasks() {
        return tasksById.values()
                .stream()
                .sorted(Comparator.comparing(Task::getCreationDate))
                .collect(Collectors.toList());
    }

    @Override
    public void removeTask(@Nonnull Long taskId) {
        tasksById.remove(taskId);
    }
}
