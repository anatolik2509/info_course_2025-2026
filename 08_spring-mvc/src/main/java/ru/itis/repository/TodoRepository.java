package ru.itis.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.model.Todo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Todo> todoRowMapper = (rs, rowNum) -> Todo.builder()
            .id(rs.getLong("id"))
            .text(rs.getString("text"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> findAll() {
        String sql = "SELECT id, text, created_at FROM todos ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, todoRowMapper);
    }

    public Optional<Todo> findById(Long id) {
        String sql = "SELECT id, text, created_at FROM todos WHERE id = ?";
        try {
            Todo todo = jdbcTemplate.queryForObject(sql, todoRowMapper, id);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Todo> findByText(String text) {
        String sql = "SELECT id, text, created_at FROM todos WHERE text = ?";
        try {
            Todo todo = jdbcTemplate.queryForObject(sql, todoRowMapper, text);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(Todo todo) {
        String sql = "INSERT INTO todos (text) VALUES (?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, todo.getText());
            return ps;
        });
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}