package com.mycompany.app.schools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {
    PhoneNumber testPhoneNumber;

    @BeforeEach
    void setUp() {
        testPhoneNumber = new PhoneNumber("1234567890");
    }

    @Test
    void testToString() {
        String expected = "(123) 456-7890";
        assertEquals(expected,testPhoneNumber.toString());


    }

    @Test
    void testEquals() {
        PhoneNumber expected = new PhoneNumber("1234567890");
        assertEquals(expected,testPhoneNumber);
    }

    @Test
    void testHashCode() {
        PhoneNumber expected = new PhoneNumber("1234567890");
        assertEquals(expected.hashCode(),testPhoneNumber.hashCode());
    }

    @Test
    void getAreaCode() {
        String expected ="123";
        assertEquals(expected,testPhoneNumber.getAreaCode());
    }
}