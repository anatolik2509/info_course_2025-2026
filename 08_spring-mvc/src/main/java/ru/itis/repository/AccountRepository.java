package ru.itis.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.model.Account;

import java.util.Optional;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Account> accountMapper = (rs, rowNum) -> Account.builder()
            .id(rs.getLong("id"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .role(rs.getString("role"))
            .build();

    public Optional<Account> findByUsername(String username) {
        try {
            Account account = jdbcTemplate.queryForObject(
                    "SELECT id, username, password, role FROM accounts WHERE username = ?",
                    accountMapper,
                    username
            );
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM accounts WHERE username = ?",
                Integer.class,
                username
        );
        return count != null && count > 0;
    }

    public void save(Account account) {
        jdbcTemplate.update(
                "INSERT INTO accounts (username, password, role) VALUES (?, ?, ?)",
                account.getUsername(),
                account.getPassword(),
                account.getRole()
        );
    }
}
