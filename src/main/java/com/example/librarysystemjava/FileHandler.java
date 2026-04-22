package com.example.librarysystemjava;

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
}
