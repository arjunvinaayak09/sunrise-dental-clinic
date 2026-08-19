package com.sunrise.service;

import com.sunrise.dao.UserDAO;
import com.sunrise.model.User;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws Exception {

        if (username == null || password == null) {
            return null;
        }

        username = username.trim();

        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        // Plain-text password comparison
        return userDAO.findByCredentials(username, password);
    }
}