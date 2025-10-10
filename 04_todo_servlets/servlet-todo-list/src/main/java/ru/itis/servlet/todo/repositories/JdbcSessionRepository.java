package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.Session;
import ru.itis.servlet.todo.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class JdbcSessionRepository implements SessionRepository{

    private static final String SESSION_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS sessions (
                session_id VARCHAR(50) PRIMARY KEY,
                user_id BIGINT,
                expire_at TIMESTAMP NOT NULL
            );
            """;

    private static final String ADD_SESSION_QUERY = """
            INSERT INTO sessions (session_id, user_id, expire_at) VALUES (?, ?, ?);
            """;

    private static final String GET_SESSION_BY_SESSION_ID_QUERY = """
            SELECT * FROM sessions WHERE session_id = ?;
            """;

    private Properties properties;
    private String url;

    public JdbcSessionRepository() {
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
            statement.executeUpdate(SESSION_TABLE_CREATE_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addSession(Long userId, String sessionId, LocalDateTime expireAt) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_SESSION_QUERY)) {
            statement.setString(1, sessionId);
            statement.setLong(2, userId);
            statement.setTimestamp(3, Timestamp.valueOf(expireAt));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session getSessionById(String sessionId) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(GET_SESSION_BY_SESSION_ID_QUERY)) {
            statement.setString(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Session session = toSession(resultSet);
                resultSet.close();
                return session;
            }
            throw new IllegalArgumentException("Session not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Session toSession(ResultSet resultSet) throws SQLException {
        return new Session(
               resultSet.getString("session_id"),
               resultSet.getLong("user_id"),
               resultSet.getTimestamp("expire_at").toLocalDateTime()
        );
    }
}
