package com.sunrise.dao;

import com.sunrise.config.DBConnection;
import com.sunrise.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE USER
    public void create(User user) throws Exception {

        String sql =
                "INSERT INTO users " +
                "(username, password_hash, role, full_name) " +
                "VALUES (?, ?, ?, ?)";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getFullName());

            ps.executeUpdate();
        }
    }


    // FIND USER FOR LOGIN
    public User findByCredentials(
            String username,
            String password) throws Exception {

        String sql =
                "SELECT id, username, password_hash, role, full_name " +
                "FROM users " +
                "WHERE username = ? AND password_hash = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapUser(rs);
                }
            }
        }

        return null;
    }


    // GET ALL USERS
    public List<User> findAll() throws Exception {

        List<User> users = new ArrayList<>();

        String sql =
                "SELECT id, username, password_hash, role, full_name " +
                "FROM users " +
                "ORDER BY id";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                users.add(mapUser(rs));
            }
        }

        return users;
    }


    // FIND USER BY ID
    public User findById(int id) throws Exception {

        String sql =
                "SELECT id, username, password_hash, role, full_name " +
                "FROM users " +
                "WHERE id = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapUser(rs);
                }
            }
        }

        return null;
    }


    // UPDATE USER
    public void update(User user) throws Exception {

        String sql =
                "UPDATE users SET " +
                "username = ?, " +
                "role = ?, " +
                "full_name = ? " +
                "WHERE id = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getFullName());
            ps.setInt(4, user.getId());

            ps.executeUpdate();
        }
    }


    // UPDATE USER INCLUDING PASSWORD
    public void updateWithPassword(User user)
            throws Exception {

        String sql =
                "UPDATE users SET " +
                "username = ?, " +
                "password_hash = ?, " +
                "role = ?, " +
                "full_name = ? " +
                "WHERE id = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getFullName());
            ps.setInt(5, user.getId());

            ps.executeUpdate();
        }
    }


    // DELETE USER
    public void delete(int id) throws Exception {

        String sql =
                "DELETE FROM users WHERE id = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }


    // CONVERT DATABASE ROW TO USER OBJECT
    private User mapUser(ResultSet rs)
            throws Exception {

        User user = new User();

        user.setId(
                rs.getInt("id")
        );

        user.setUsername(
                rs.getString("username")
        );

        user.setPasswordHash(
                rs.getString("password_hash")
        );

        user.setRole(
                rs.getString("role")
        );

        user.setFullName(
                rs.getString("full_name")
        );

        return user;
    }
}