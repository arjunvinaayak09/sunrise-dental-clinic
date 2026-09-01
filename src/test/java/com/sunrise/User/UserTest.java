package com.sunrise.User;

import com.sunrise.model.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {

        User user = new User();

        user.setId(1);
        user.setUsername("admin");
        user.setPasswordHash("admin123");
        user.setRole("ADMIN");
        user.setFullName("System Administrator");

        assertEquals(1, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin123", user.getPasswordHash());
        assertEquals("ADMIN", user.getRole());
        assertEquals(
                "System Administrator",
                user.getFullName()
        );
    }

    @Test
    public void testUsername() {

        User user = new User();

        user.setUsername("receptionist");

        assertEquals(
                "receptionist",
                user.getUsername()
        );
    }

    @Test
    public void testRole() {

        User user = new User();

        user.setRole("DOCTOR");

        assertEquals(
                "DOCTOR",
                user.getRole()
        );
    }
}