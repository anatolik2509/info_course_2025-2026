package ru.itis.servlet.todo.repositories;

import ru.itis.servlet.todo.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUserRepository implements UserRepository{

    private static final String USER_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                login varchar(100) NOT NULL,
                password_hash varchar(512) NOT NULL,
                salt varchar(50) NOT NULL
            );
            """;

    private static final String ADD_USER_QUERY = """
            INSERT INTO users (login, password_hash, salt) VALUES (?, ?, ?) RETURNING id;
            """;

    private static final String GET_USER_BY_LOGIN_QUERY = """
            SELECT * FROM users WHERE login = ?;
            """;

    private static final String GET_USER_BY_ID_QUERY = """
            SELECT * FROM users WHERE id = ?;
            """;

    private final Properties properties;
    private final String url;

    public JdbcUserRepository() {
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
            statement.executeUpdate(USER_TABLE_CREATE_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long createUser(User user) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getSalt());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long createdId = resultSet.getLong(1);
                resultSet.close();
                return createdId;
            }
            throw new IllegalArgumentException("User id not created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_QUERY)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = toUser(resultSet);
                resultSet.close();
                return user;
            }
            throw new IllegalArgumentException("User not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = toUser(resultSet);
                resultSet.close();
                return user;
            }
            throw new IllegalArgumentException("User not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User toUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("login"),
                resultSet.getString("password_hash"),
                resultSet.getString("salt")
        );
    }
}
