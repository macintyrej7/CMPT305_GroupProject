package com.mycompany.app.schools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    Address testAddress;
    @BeforeEach
    void setUp() {
        testAddress = new Address("1234","Fake Street");
    }

    @Test
    void testToString() {
        String expected = "1234 Fake Street";
        assertEquals(expected,testAddress);
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}