package com.mycompany.app.properties;

import static org.junit.jupiter.api.Assertions.*;


import com.mycompany.app.properties.Address;
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
        assertEquals(expected,testAddress.toString());
    }

    @Test
    void testEquals() {
        Address expectedAddress = new Address("1234","Fake Street");
        assertEquals(expectedAddress,testAddress);
    }

    @Test
    void testHashCode() {
        Address expectedAddress = new Address("1234","Fake Street");
        assertEquals(expectedAddress.hashCode(),testAddress.hashCode());

    }
}