package ru.itis.servlet.todo.services;

import ru.itis.servlet.todo.exceptions.AuthenticationException;
import ru.itis.servlet.todo.models.Session;
import ru.itis.servlet.todo.models.User;
import ru.itis.servlet.todo.repositories.SessionRepository;
import ru.itis.servlet.todo.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Base64.Encoder base64Encoder;
    private final Duration sessionDuration;

    public SecurityServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.base64Encoder = Base64.getEncoder();
        // вообще такое по хорошему выносится в property файлы
        this.sessionDuration = Duration.ofMinutes(1);
    }

    @Override
    public String registerUser(String email, String password, String passwordRepeat) {
        if (!password.equals(passwordRepeat)) {
            throw new AuthenticationException("Passwords doesnt match");
        }
        String salt = UUID.randomUUID().toString();
        String saltedPassword = password + salt;
        String passwordHash = getPasswordHash(saltedPassword);
        User user = new User(null, email, passwordHash, salt);
        Long userId = userRepository.createUser(user);
        String sessionId = UUID.randomUUID().toString();
        sessionRepository.addSession(userId, sessionId, LocalDateTime.now().plus(sessionDuration));
        return sessionId;
    }

    @Override
    public String loginUser(String email, String password) {
        User user;
        try {
            user = userRepository.getUserByLogin(email);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Email not found");
        }
        String salt = user.getSalt();
        String saltedPassword = password + salt;
        String passwordHash = getPasswordHash(saltedPassword);
        if (!passwordHash.equals(user.getPasswordHash())) {
            throw new AuthenticationException("Invalid password");
        }
        String sessionId = UUID.randomUUID().toString();
        sessionRepository.addSession(user.getId(), sessionId, LocalDateTime.now().plus(sessionDuration));
        return sessionId;
    }

    @Override
    public User getUser(String sessionId) {
        Session session;
        try {
            session = sessionRepository.getSessionById(sessionId);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Session not found");
        }
        if (session.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new AuthenticationException("Session expired");
        }
        return userRepository.getUserById(session.getUserId());
    }

    private String getPasswordHash(String saltedPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return base64Encoder.encodeToString(passwordHashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
