package org.example.notesapp.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.server.ServerContainer;
import lombok.extern.slf4j.Slf4j;
import org.example.notesapp.websocket.NoteWebSocketEndpoint;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@WebListener
@Slf4j
public class WebSocketConfig implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting web sockets");
        ServerContainer serverContainer = (ServerContainer)
                servletContextEvent.getServletContext().getAttribute("jakarta.websocket.server.ServerContainer");
        try {
            serverContainer.addEndpoint(NoteWebSocketEndpoint.class);
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        }
    }
}