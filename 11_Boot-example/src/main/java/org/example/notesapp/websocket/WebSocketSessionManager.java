package org.example.notesapp.websocket;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Хранит WebSocket-сессии, сгруппированные по имени пользователя.
 * Позволяет рассылать сообщения во все открытые вкладки конкретного пользователя.
 */
@Slf4j
public class WebSocketSessionManager {

    // username -> множество его открытых WebSocket-сессий (разные вкладки браузера)
    private static final ConcurrentHashMap<String, Set<Session>> sessions = new ConcurrentHashMap<>();

    public static void register(String username, Session session) {
        sessions.computeIfAbsent(username, k -> new CopyOnWriteArraySet<>()).add(session);
        log.info("WebSocket подключён: {} (сессий: {})", username, sessions.get(username).size());
    }

    public static void unregister(String username, Session session) {
        Set<Session> userSessions = sessions.get(username);
        if (userSessions != null) {
            userSessions.remove(session);
            if (userSessions.isEmpty()) {
                sessions.remove(username);
            }
            log.info("WebSocket отключён: {} (осталось сессий: {})",
                    username, sessions.getOrDefault(username, Set.of()).size());
        }
    }

    /**
     * Отправляет JSON-сообщение во все WebSocket-сессии указанного пользователя.
     */
    public static void sendToUser(String username, String jsonMessage) {
        Set<Session> userSessions = sessions.get(username);
        if (userSessions == null) {
            return;
        }
        for (Session session : userSessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(jsonMessage);
                } catch (IOException e) {
                    log.error("Ошибка отправки WebSocket-сообщения пользователю {}: {}", username, e.getMessage());
                }
            }
        }
    }
}