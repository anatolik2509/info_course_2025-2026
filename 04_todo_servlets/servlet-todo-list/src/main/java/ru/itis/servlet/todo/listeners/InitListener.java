package ru.itis.servlet.todo.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.servlet.todo.repositories.InMemoryTaskRepository;
import ru.itis.servlet.todo.repositories.JdbcTaskRepository;
import ru.itis.servlet.todo.repositories.TaskRepository;
import ru.itis.servlet.todo.services.TodoListService;
import ru.itis.servlet.todo.services.TodoListServiceImpl;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TaskRepository taskRepository = new JdbcTaskRepository();
        TodoListService todoListService = new TodoListServiceImpl(taskRepository);
        sce.getServletContext().setAttribute("todoListService", todoListService);
    }
}
