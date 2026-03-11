package ru.itis.service;

import org.springframework.stereotype.Service;
import ru.itis.exception.TodoAlreadyExistsException;
import ru.itis.model.Todo;
import ru.itis.repository.TodoRepository;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public void createTodo(String text) {
        if (todoRepository.findByText(text).isPresent()) {
            throw new TodoAlreadyExistsException("Todo with text '" + text + "' already exists");
        }

        Todo todo = Todo.builder()
                .text(text)
                .build();

        todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}