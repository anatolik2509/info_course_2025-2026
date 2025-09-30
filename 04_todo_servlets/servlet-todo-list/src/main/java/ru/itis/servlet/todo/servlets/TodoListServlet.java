package ru.itis.servlet.todo.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.servlet.todo.models.Task;
import ru.itis.servlet.todo.services.TodoListService;

import java.io.IOException;
import java.util.List;

@WebServlet("/list")
public class TodoListServlet extends HttpServlet {
    private TodoListService todoListService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.todoListService = (TodoListService) config.getServletContext().getAttribute("todoListService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = todoListService.getTasks();
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/views/todo-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        todoListService.addTask(req.getParameter("taskText"));
        resp.sendRedirect(req.getContextPath() + "/list");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        todoListService.removeTask(Long.parseLong(req.getParameter("taskId")));
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
