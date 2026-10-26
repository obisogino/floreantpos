package com.floreantpos.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Existing stored login hashes must survive the Java upgrade. */
class PasswordHasherBehaviorTest {

    @Test
    void existingNumericPinKeepsItsStoredHash() {
        assertEquals("40bd001563085fc35165329ea1ff5c5ecbdbbeef",
                PasswordHasher.hashPassword("123"));
    }

    @Test
    void emptyPasswordKeepsItsExistingHash() {
        assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709",
                PasswordHasher.hashPassword(""));
    }

    @Test
    void nonAsciiPasswordUsesTheExistingUtf8Bytes() {
        assertEquals("addccc17e6cee5857692de3ac0739441a83047b7",
                PasswordHasher.hashPassword("p\u00e4ss\u20b1"));
    }
}
