package com.example.librarysystemjava;

import com.example.librarysystemjava.Validator;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void isValidStudentId() {
        Validator validator = new Validator();
        // Valid case: 8 digits
        assertTrue("Should be valid: 12345678", validator.isValidStudentId("12345678"));

        // Invalid cases
        assertFalse("Too short", validator.isValidStudentId("1234567"));
        assertFalse("Too long", validator.isValidStudentId("123456789"));
        assertFalse("Contains letters", validator.isValidStudentId("1234567A"));
        assertFalse("Null check", validator.isValidStudentId(null));
    }

    @Test
    public void isValidIsbn() {
        Validator validator = new Validator();
        // Valid ISBN
        assertTrue("Standard valid ISBN should pass", validator.isValidIsbn("9780131103627"));

        // Valid with formatting
        assertTrue("Should handle hyphens", validator.isValidIsbn("978-0131103627"));

        // Invalid Checksum
        assertFalse("Wrong checksum should fail", validator.isValidIsbn("9780131103628"));

        // Invalid Prefix
        assertFalse("Prefixes other than 978/979 should fail", validator.isValidIsbn("1230131103627"));

        // Invalid Length
        assertFalse("12 digits should fail", validator.isValidIsbn("978013110362"));
    }

    @Test
    public void isValidCopies() {
        Validator validator = new Validator();
        // Coursework requirement: Max 2 copies
        assertTrue("0 is valid", validator.isValidCopies(0));
        assertTrue("1 is valid", validator.isValidCopies(1));
        assertTrue("2 is valid", validator.isValidCopies(2));

        // Invalid cases
        assertFalse("3 copies should fail (Max 2)", validator.isValidCopies(3));
        assertFalse("Negative numbers should fail", validator.isValidCopies(-1));
    }

    @Test
    public void isValidAvailability() {
        Validator validator = new Validator();
        // Valid cases: availability <= copies
        assertTrue("Availability equal to copies should pass", validator.isValidAvailability(2, 2));
        assertTrue("Availability less than copies should pass", validator.isValidAvailability(1, 2));

        // Invalid cases
        assertFalse("Availability higher than copies should fail", validator.isValidAvailability(3, 2));
        assertFalse("Negative availability should fail", validator.isValidAvailability(-1, 2));
    }

    @Test
    public void isValidDate() {
        Validator validator = new Validator();
        // Valid date: dd/MM/yyyy
        assertTrue("Standard date should pass", validator.isValidDate("15/04/2026"));

        // Invalid formats
        assertFalse("Wrong separator should fail", validator.isValidDate("15-04-2026"));
        assertFalse("US format should fail", validator.isValidDate("04/15/2026"));

        // Logical errors (Strict check)
        assertFalse("Non-existent date should fail", validator.isValidDate("32/01/2026"));
        assertFalse("Invalid month should fail", validator.isValidDate("10/13/2026"));
    }
}