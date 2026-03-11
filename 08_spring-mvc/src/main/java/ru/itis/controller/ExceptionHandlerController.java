package ru.itis.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.exception.TodoAlreadyExistsException;
import ru.itis.service.TodoService;

@ControllerAdvice
public class ExceptionHandlerController {

    private final TodoService todoService;

    public ExceptionHandlerController(TodoService todoService) {
        this.todoService = todoService;
    }

    @ExceptionHandler(TodoAlreadyExistsException.class)
    public String handleTodoAlreadyExists(TodoAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("todos", todoService.getAllTodos());
        return "todo";
    }
}
