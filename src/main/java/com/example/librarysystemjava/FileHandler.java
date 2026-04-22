package com.example.librarysystemjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    // Valid data lists
    private List<Book> validBooks =  new ArrayList<>();
    private List<Student> validStudents =  new ArrayList<>();
    private List<Transaction> validTransactions =  new ArrayList<>();

    // Invalid data lists
    private List<String> invalidBooks =  new ArrayList<>();
    private List<String> invalidStudents =  new ArrayList<>();
    private List<String> invalidTransactions =  new ArrayList<>();

    //Load Books method
    public void loadBooks(String filePath) {
        // Makes sure the lists are empty
        validBooks.clear();
        invalidBooks.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Used to skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Adds book info to the book array
                if (data.length == 6) { // matches the number of rows in csv file
                    try {
                        String bookId = data[0].trim();
                        String isbn = data[1].trim();
                        String title = data[2].trim();
                        int copies = Integer.parseInt(data[3].trim());
                        int availability = Integer.parseInt(data[4].trim());
                        double price = Double.parseDouble(data[5].trim());

                        // Validation
                        if (Validator.isValidIsbn(isbn) && Validator.isValidCopies(copies) && Validator.isValidAvailability(availability, copies)) {
                            validBooks.add(new Book(bookId, isbn, title, copies, availability, price));
                        } else {
                            invalidBooks.add(line);
                        }

                    } catch (NumberFormatException e) {
                        invalidBooks.add(line);
                    }
                } else {
                    invalidBooks.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}
