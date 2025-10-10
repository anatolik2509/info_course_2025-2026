package ru.itis.servlet.todo.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.servlet.todo.repositories.*;
import ru.itis.servlet.todo.services.SecurityService;
import ru.itis.servlet.todo.services.SecurityServiceImpl;
import ru.itis.servlet.todo.services.TodoListService;
import ru.itis.servlet.todo.services.TodoListServiceImpl;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepository userRepository = new JdbcUserRepository();
        SessionRepository sessionRepository = new JdbcSessionRepository();

        SecurityService securityService = new SecurityServiceImpl(userRepository, sessionRepository);

        TaskRepository taskRepository = new JdbcTaskRepository();
        TodoListService todoListService = new TodoListServiceImpl(taskRepository);
        sce.getServletContext().setAttribute("todoListService", todoListService);
        sce.getServletContext().setAttribute("securityService", securityService);
    }
}
