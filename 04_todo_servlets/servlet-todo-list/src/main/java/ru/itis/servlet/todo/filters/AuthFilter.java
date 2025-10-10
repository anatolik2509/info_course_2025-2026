package ru.itis.servlet.todo.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.servlet.todo.exceptions.AuthenticationException;
import ru.itis.servlet.todo.models.User;
import ru.itis.servlet.todo.services.SecurityService;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "*")
public class AuthFilter extends HttpFilter {

    private final List<String> protectedPaths;
    private SecurityService securityService;

    public AuthFilter() {
        this.protectedPaths = List.of("/profile");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.securityService = (SecurityService) config.getServletContext().getAttribute("securityService");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!protectedPaths.contains(req.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }
        String sessionId = extractSessionId(req.getCookies());
        if (sessionId == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user;
        try {
            user = securityService.getUser(sessionId);
        } catch (AuthenticationException e) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("user", user);
        chain.doFilter(req, res);
    }

    private String extractSessionId(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
