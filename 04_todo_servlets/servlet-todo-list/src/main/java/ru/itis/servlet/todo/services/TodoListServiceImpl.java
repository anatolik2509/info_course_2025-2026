package ru.itis.servlet.todo.services;

import ru.itis.servlet.todo.models.Task;
import ru.itis.servlet.todo.repositories.TaskRepository;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;

public class TodoListServiceImpl implements TodoListService {

    private final TaskRepository taskRepository;

    public TodoListServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(String taskText) {
        if (taskText == null || taskText.isBlank()) {
            return;
        }
        taskRepository.addTask(new Task(null, taskText, LocalDateTime.now()));
    }

    @Nonnull
    @Override
    public List<Task> getTasks() {
        return taskRepository.getTasks();
    }

    @Override
    public void removeTask(Long taskId) {
        if (taskId == null) {
            return;
        }
        taskRepository.removeTask(taskId);
    }
}
