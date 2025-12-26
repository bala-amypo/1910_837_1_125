package com.example.demo.servlet;
import jakarta.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (resp == null) throw new RuntimeException();
        resp.setContentType("text/plain");
        resp.getWriter().write("Hello from servlet");
    }
}