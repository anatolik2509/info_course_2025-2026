package ru.itis.servlet.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.*;

import java.io.IOException;

import static java.util.logging.Level.INFO;

public class LoggingFilter extends HttpFilter {

    private Logger logger;

    @Override
    public void init() throws ServletException {
        logger = Logger.getLogger("http-logger");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.log(INFO, req.getRequestURI());
        chain.doFilter(req, res);
        logger.log(INFO, String.valueOf(res.getStatus()));
    }
}
