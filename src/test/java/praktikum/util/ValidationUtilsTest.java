package com.praktikum.testing.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "test.name@domain.co.id"})
    @DisplayName("Test email valid")
    void testIsValidEmail_Valid(String email) {
        assertTrue(ValidationUtils.isValidEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "test@", "test@domain", "test@.com"})
    @DisplayName("Test email tidak valid")
    void testIsValidEmail_Invalid(String email) {
        assertFalse(ValidationUtils.isValidEmail(email));
    }

    @Test
    @DisplayName("Test email null")
    void testIsValidEmail_Null() {
        assertFalse(ValidationUtils.isValidEmail(null));
    }
}