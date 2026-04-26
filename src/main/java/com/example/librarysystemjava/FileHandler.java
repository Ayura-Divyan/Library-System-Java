package com.example.librarysystemjava;

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

    public void syncData(List<String> books, List<String> students, List<String> transactions) {
        validBooks.clear(); invalidBooks.clear();
        validStudents.clear(); invalidStudents.clear();
        validTransactions.clear(); invalidTransactions.clear();

        //  Process Books
        for (String line : books) {
            String[] data = line.split(",", -1);
            boolean isValid = false;
            if (data.length == 6) {
                try {
                    String isbn = data[1].trim();
                    int copies = Integer.parseInt(data[3].trim());
                    if (Validator.isValidIsbn(isbn) && Validator.isValidCopies(copies)) {
                        validBooks.add(new Book(data[0].trim(), isbn, data[2].trim(), copies,
                                Integer.parseInt(data[4].trim()), Double.parseDouble(data[5].trim())));
                        isValid = true;
                    }
                } catch (Exception e) {}
            }
            if (!isValid) invalidBooks.add(line);
        }

        // Process Students
        for (String line : students) {
            String[] data = line.split(",", -1);
            boolean isValid = false;
            if (data.length == 2 && Validator.isValidStudentId(data[0].trim())) {
                validStudents.add(new Student(data[0].trim(), data[1].trim()));
                isValid = true;
            }
            if (!isValid) invalidStudents.add(line);
        }

        // Process Transactions
        for (String line : transactions) {
            String[] data = line.split(",", -1);
            boolean isValid = false;
            if (data.length == 5 && Validator.isValidDate(data[1].trim())) {
                validTransactions.add(new Transaction(data[0].trim(), data[1].trim(), data[2].trim(),
                        data[3].trim(), Integer.parseInt(data[4].trim())));
                isValid = true;
            }
            if (!isValid) invalidTransactions.add(line);
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
