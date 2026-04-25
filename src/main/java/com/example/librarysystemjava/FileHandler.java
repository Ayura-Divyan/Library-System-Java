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
                            validBooks.add(new Book(bookId, isbn, title, copies, availability, price)); // Creates a book object
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

    // Load Students method
    public void loadStudents(String filePath) {
        // Makes sure the lists are empty
        validStudents.clear();
        invalidStudents.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Used to skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Adds student info to the book array
                if (data.length == 2) { // matches the number of rows in csv file
                    try {
                        String  studentId = data[0].trim();
                        String  firstName = data[1].trim();

                        // Validation
                        if (Validator.isValidStudentId(studentId)) {
                            validStudents.add(new Student(studentId, firstName)); // Creates a student object
                        } else {
                            invalidStudents.add(line);
                        }

                    } catch (NumberFormatException e) {
                        invalidStudents.add(line);
                    }
                } else {
                    invalidStudents.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Load Transactions method
    public void loadTransactions(String filePath) {
        // Makes sure the lists are empty
        validTransactions.clear();
        invalidTransactions.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Used to skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Adds transaction info to the book array
                if (data.length == 5) { // matches the number of rows in csv file
                    try {
                        String  transactionId = data[0].trim();
                        String date = data[1].trim();
                        String bookId = data[2].trim();
                        String studentId = data[3].trim();
                        int type = Integer.parseInt(data[4].trim());

                        // Validation
                        if (Validator.isValidDate(date) && (type == 1 || type == 2) && Validator.isValidStudentId(studentId)) {
                            validTransactions.add(new Transaction(transactionId, date, bookId, studentId, type)); // Creates a transaction object
                        } else {
                            invalidTransactions.add(line);
                        }

                    } catch (NumberFormatException e) {
                        invalidTransactions.add(line);
                    }
                } else {
                    invalidTransactions.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Getters for data retrieval
    public List<Book> getValidBooks() {
        return validBooks;
    }
    public List<String> getInvalidBooks() {
        return invalidBooks;
    }

    public List<Student> getValidStudents() {
        return  validStudents;
    }
    public List<String> getInvalidStudents() {
        return invalidStudents;
    }

    public List<Transaction> getValidTransactions() {
        return validTransactions;
    }
    public List<String> getInvalidTransactions() {
        return invalidTransactions;
    }

    public void updateRecordValidation(String oldRecord, String newRecord, List<String> targetInvalidList) {
        targetInvalidList.remove(oldRecord);

        String[] data = newRecord.split(",");
        boolean isValid = false;

        if (data.length == 2) { // Student
            isValid = Validator.isValidStudentId(data[0].trim());
        } else if (data.length == 5) { // Transaction
            isValid = Validator.isValidDate(data[1].trim());
        } else if (data.length == 6) { // Book
            try {
                isValid = Validator.isValidIsbn(data[1].trim()) &&
                        Validator.isValidCopies(Integer.parseInt(data[3].trim()));
            } catch (Exception e) {isValid = false;}
        }
        if (!isValid) {
            targetInvalidList.add(newRecord);
        }
    }

    public void saveAllData(String dir, List<String> books, List<String> students, List<String> transactions) {
        try {
            // Save Books
            java.io.PrintWriter bw = new java.io.PrintWriter(new java.io.FileWriter(dir + "/book.csv"));
            bw.println("book_id,isbn,title,copies,availability,price");
            for (String b : books) bw.println(b);
            bw.close();

            // Save Students
            java.io.PrintWriter sw = new java.io.PrintWriter(new java.io.FileWriter(dir + "/student.csv"));
            sw.println("student_id,first_name"); // Header
            for (String s : students) sw.println(s);
            sw.close();

            // Save Transactions
            java.io.PrintWriter tw = new java.io.PrintWriter(new java.io.FileWriter(dir + "/transaction.csv"));
            tw.println("transaction_id,date,book_id,student_id,type"); // Header
            for (String t : transactions) tw.println(t);
            tw.close();
        } catch (IOException e) {
            System.out.println("Error saving file:  " + e.getMessage());
        }
    }
}
