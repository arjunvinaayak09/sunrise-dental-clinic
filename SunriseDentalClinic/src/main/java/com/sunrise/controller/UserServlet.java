package com.sunrise.controller;

import com.sunrise.dao.UserDAO;
import com.sunrise.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserDAO dao = new UserDAO();


    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {

            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Administrator access required"
            );

            return;
        }

        try {

            String action =
                    req.getParameter("action");

            // EDIT USER
            if ("edit".equals(action)) {

                String idValue =
                        req.getParameter("id");

                if (idValue == null) {

                    resp.sendRedirect(
                            req.getContextPath()
                            + "/users"
                    );

                    return;
                }

                int id =
                        Integer.parseInt(idValue);

                User user =
                        dao.findById(id);

                if (user == null) {

                    resp.sendRedirect(
                            req.getContextPath()
                            + "/users?msg=notfound"
                    );

                    return;
                }

                req.setAttribute(
                        "editUser",
                        user
                );
            }

            // GET ALL USERS
            List<User> users =
                    dao.findAll();

            req.setAttribute(
                    "users",
                    users
            );

            String message =
                    req.getParameter("msg");

            if ("created".equals(message)) {

                req.setAttribute(
                        "successMessage",
                        "User created successfully"
                );

            } else if ("updated".equals(message)) {

                req.setAttribute(
                        "successMessage",
                        "User updated successfully"
                );

            } else if ("deleted".equals(message)) {

                req.setAttribute(
                        "successMessage",
                        "User deleted successfully"
                );

            } else if ("notfound".equals(message)) {

                req.setAttribute(
                        "errorMessage",
                        "User not found"
                );
            }

            req.getRequestDispatcher(
                    "/WEB-INF/views/manage-users.jsp"
            ).forward(req, resp);

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to load users",
                    e
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {

            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Administrator access required"
            );

            return;
        }

        try {

            String action =
                    req.getParameter("action");

            // =========================
            // CREATE
            // =========================

            if ("create".equals(action)) {

                String username =
                        req.getParameter("username");

                String password =
                        req.getParameter("password");

                String role =
                        req.getParameter("role");

                String fullName =
                        req.getParameter("fullName");

                if (isEmpty(username)
                        || isEmpty(password)
                        || isEmpty(role)
                        || isEmpty(fullName)) {

                    redirectWithMessage(
                            resp,
                            req,
                            "empty"
                    );

                    return;
                }

                User user =
                        new User();

                user.setUsername(
                        username.trim()
                );

                // Plain-text password
                user.setPasswordHash(
                        password
                );

                user.setRole(
                        role.trim().toUpperCase()
                );

                user.setFullName(
                        fullName.trim()
                );

                dao.create(user);

                resp.sendRedirect(
                        req.getContextPath()
                        + "/users?msg=created"
                );

                return;
            }


            // =========================
            // UPDATE
            // =========================

            if ("update".equals(action)) {

                int id =
                        Integer.parseInt(
                                req.getParameter("id")
                        );

                String username =
                        req.getParameter("username");

                String password =
                        req.getParameter("password");

                String role =
                        req.getParameter("role");

                String fullName =
                        req.getParameter("fullName");

                if (isEmpty(username)
                        || isEmpty(role)
                        || isEmpty(fullName)) {

                    redirectWithMessage(
                            resp,
                            req,
                            "empty"
                    );

                    return;
                }

                User user =
                        new User();

                user.setId(id);

                user.setUsername(
                        username.trim()
                );

                user.setRole(
                        role.trim().toUpperCase()
                );

                user.setFullName(
                        fullName.trim()
                );


                // If password is empty,
                // keep the old password.
                if (isEmpty(password)) {

                    dao.update(user);

                } else {

                    user.setPasswordHash(
                            password
                    );

                    dao.updateWithPassword(
                            user
                    );
                }

                resp.sendRedirect(
                        req.getContextPath()
                        + "/users?msg=updated"
                );

                return;
            }


            // =========================
            // DELETE
            // =========================

            if ("delete".equals(action)) {

                int id =
                        Integer.parseInt(
                                req.getParameter("id")
                        );

                dao.delete(id);

                resp.sendRedirect(
                        req.getContextPath()
                        + "/users?msg=deleted"
                );

                return;
            }


            resp.sendRedirect(
                    req.getContextPath()
                    + "/users"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(
                    "User operation failed",
                    e
            );
        }
    }


    private boolean isAdmin(
            HttpServletRequest req) {

        HttpSession session =
                req.getSession(false);

        if (session == null) {
            return false;
        }

        Object object =
                session.getAttribute("user");

        if (!(object instanceof User)) {
            return false;
        }

        User user =
                (User) object;

        return user.getRole() != null
                && "ADMIN".equalsIgnoreCase(
                        user.getRole().trim()
                );
    }


    private boolean isEmpty(String value) {

        return value == null
                || value.trim().isEmpty();
    }


    private void redirectWithMessage(
            HttpServletResponse resp,
            HttpServletRequest req,
            String message)
            throws IOException {

        resp.sendRedirect(
                req.getContextPath()
                + "/users?msg="
                + message
        );
    }
}