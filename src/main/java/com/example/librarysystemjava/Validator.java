package com.example.librarysystemjava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validator {
    // Validates the student ID
    public static boolean isValidStudentId(String id){
        return id != null && id.matches("\\d{8}");
    }

    // Validates the ISBN
    public static boolean isValidIsbn(String isbn){
        // Remove Hyphen and spaces
        String cleanIsbn = isbn.replaceAll("\\s-", "");

        // Length check
        if(cleanIsbn.length() != 13 || !cleanIsbn.matches("\\d{13}")){
            return false;
        }

        if (!cleanIsbn.startsWith("978") && !cleanIsbn.startsWith("979")) {
            return false;
        }

        // Checksum Calculation
        int total = 0;
        for (int i = 0; i <= isbn.length(); i++) {
            int digit = Character.getNumericValue(cleanIsbn.charAt(i));

            if (i % 2 == 0) {
                    total += digit;
            } else {
                total += digit * 3;
            }
        }
        return total % 10 == 0;
    }

    // Validates the copies
    public static boolean isValidCopies(int copies) {
        return copies >= 0 && copies <= 2;
    }

    // Validate Availability
    public static boolean isValidAvailability(int availability, int copies) {
        return availability >= 0 && availability <= copies;
    }

    // Validate Date
    public static boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }
}
