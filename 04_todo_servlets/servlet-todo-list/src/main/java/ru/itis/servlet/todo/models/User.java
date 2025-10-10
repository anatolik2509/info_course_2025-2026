package ru.itis.servlet.todo.models;

public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private String salt;

    public User(Long id, String email, String passwordHash, String salt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}
