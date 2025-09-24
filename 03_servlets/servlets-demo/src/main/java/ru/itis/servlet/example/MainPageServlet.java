package ru.itis.servlet.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MainPageServlet extends HttpServlet {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "text/html";
    private static final String HTML_RESPONSE = """
    <html>
        <body>
            <h2>Hello World! CCCC</h2>
        </body>
    </html>
    """;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE);
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = resp.getWriter();
        out.write(HTML_RESPONSE);
    }
}
