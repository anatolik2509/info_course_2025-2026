package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.Task;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcTaskRepository implements TaskRepository {

    private static final String TASK_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS tasks (
                id BIGSERIAL PRIMARY KEY,
                text TEXT NOT NULL,
                created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;

    private static final String ADD_TASK_QUERY = """
            INSERT INTO tasks (text) VALUES (?);
            """;

    private static final String GET_ALL_TASKS_QUERY = """
            SELECT * FROM tasks;
            """;

    private static final String DELETE_TASK_BY_ID_QUERY = """
            DELETE FROM tasks WHERE id = %d;
            """;

    private final String url;
    private final Properties properties;

    public JdbcTaskRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("url");
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(TASK_TABLE_CREATE_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTask(@Nonnull Task task) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_TASK_QUERY)) {
            statement.setString(1, task.getTaskText());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @Override
    public List<Task> getTasks() {
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_TASKS_QUERY)) {
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(toTask(resultSet));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTask(@Nonnull Long taskId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_TASK_BY_ID_QUERY.formatted(taskId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Task toTask(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getLong("id"),
                resultSet.getString(2),
                resultSet.getTimestamp(3).toLocalDateTime()
        );
    }
}
