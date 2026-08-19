package com.sunrise.controller;

import com.sunrise.model.User;
import com.sunrise.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = authService.login(req.getParameter("username"), req.getParameter("password"));
            if (user == null) {
                resp.sendRedirect("index.jsp?error=invalid");
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
