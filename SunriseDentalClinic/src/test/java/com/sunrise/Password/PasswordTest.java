package com.sunrise.Password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    public void testCorrectPassword() {

        String databasePassword = "admin123";
        String enteredPassword = "admin123";

        assertEquals(
                databasePassword,
                enteredPassword
        );
    }

    @Test
    public void testWrongPassword() {

        String databasePassword = "admin123";
        String enteredPassword = "wrong123";

        assertNotEquals(
                databasePassword,
                enteredPassword
        );
    }

    @Test
    public void testEmptyPassword() {

        String password = "";

        assertTrue(password.isEmpty());
    }
}