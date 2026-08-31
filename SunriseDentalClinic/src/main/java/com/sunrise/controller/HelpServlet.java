package com.sunrise.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunrise.model.User;

import java.io.IOException;

@WebServlet("/help")
public class HelpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // User must be logged in
        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );

            return;
        }

        // Check User object
        Object obj = session.getAttribute("user");

        if (!(obj instanceof User)) {

            session.invalidate();

            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );

            return;
        }

     // Open help.jsp (lives under WEB-INF/views like the other views,
     // so it isn't directly browsable and must go through this servlet)
     request.getRequestDispatcher(
             "/WEB-INF/views/help.jsp"
     ).forward(request, response);
    }
}