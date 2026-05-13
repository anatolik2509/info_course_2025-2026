package org.example.notesapp.websocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Jakarta WebSocket (JSR 356) эндпоинт для real-time синхронизации заметок.
 *
 * Каждый клиент (вкладка браузера) подключается сюда по ws://host/ws/notes.
 * При подключении из HTTP-сессии извлекается Spring Security Principal,
 * после чего сессия регистрируется в WebSocketSessionManager.
 *
 * На каждое подключение - свой экземпляр ServerEndpoint
 */
@Slf4j
@ServerEndpoint(value = "/ws/notes", configurator = NoteWebSocketEndpoint.HttpSessionConfigurator.class)
public class NoteWebSocketEndpoint {

    private Session session;
    private String username;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        this.username = (String) config.getUserProperties().get("username");
        log.info("Пришёл вебсокет");

        if (username == null) {
            log.warn("WebSocket: подключение без аутентификации, закрываем");
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Not authenticated"));
            } catch (Exception e) {
                log.error("Ошибка закрытия сессии: {}", e.getMessage());
            }
            return;
        }

        WebSocketSessionManager.register(username, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if (username != null) {
            WebSocketSessionManager.unregister(username, session);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket ошибка ({}): {}", username, throwable.getMessage());
        if (username != null) {
            WebSocketSessionManager.unregister(username, session);
        }
    }

    // в данном случаем метод бесполезен, но это пример, как обрабатывать сообщения на стороне сервера
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info(message);
    }

    /**
     * Конфигуратор, который перехватывает HTTP-рукопожатие (handshake)
     * и извлекает имя пользователя из Spring Security контекста HTTP-сессии.
     */
    public static class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

        @Override
        public void modifyHandshake(ServerEndpointConfig config,
                                    HandshakeRequest request,
                                    HandshakeResponse response) {
            HttpSession httpSession = (HttpSession) request.getHttpSession();
            if (httpSession != null) {
                SecurityContext securityContext = (SecurityContext)
                        httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
                if (securityContext != null && securityContext.getAuthentication() != null) {
                    String username = securityContext.getAuthentication().getName();
                    config.getUserProperties().put("username", username);
                }
            }
        }
    }
}