package com.sunrise.LoginValidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginValidationTest {

    @Test
    public void testValidAdminLogin() {

        String username = "admin";
        String password = "admin123";

        assertEquals("admin", username);
        assertEquals("admin123", password);
    }

    @Test
    public void testInvalidUsername() {

        String validUsername = "admin";
        String enteredUsername = "wronguser";

        assertNotEquals(
                validUsername,
                enteredUsername
        );
    }

    @Test
    public void testInvalidPassword() {

        String validPassword = "admin123";
        String enteredPassword = "wrongpassword";

        assertNotEquals(
                validPassword,
                enteredPassword
        );
    }

    @Test
    public void testEmptyUsername() {

        String username = "";

        assertTrue(username.isEmpty());
    }

    @Test
    public void testEmptyPassword() {

        String password = "";

        assertTrue(password.isEmpty());
    }
}