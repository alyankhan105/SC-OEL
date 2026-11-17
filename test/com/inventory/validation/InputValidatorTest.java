package com.inventory.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    void shouldRejectNullEmptyAndWhitespaceNames() {
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateName(null));
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateName(""));
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateName(" \t\n"));
    }

    @Test
    void shouldAcceptNonblankName() {
        assertDoesNotThrow(() -> InputValidator.validateName(" Product "));
    }

    @Test
    void shouldRejectNegativeQuantityAndAcceptZeroOrPositiveQuantity() {
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateQuantity(-1));
        assertDoesNotThrow(() -> InputValidator.validateQuantity(0));
        assertDoesNotThrow(() -> InputValidator.validateQuantity(25));
    }

    @Test
    void shouldRejectNonpositiveIdsAndAcceptPositiveId() {
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateItemId(0));
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateItemId(-5));
        assertDoesNotThrow(() -> InputValidator.validateItemId(1));
    }
}
