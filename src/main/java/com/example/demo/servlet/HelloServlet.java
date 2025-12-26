package com.example.demo.servlet;
import jakarta.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (response == null) throw new RuntimeException("Null response");
        response.setContentType("text/plain");
        response.getWriter().write("Hello from servlet");
    }
}