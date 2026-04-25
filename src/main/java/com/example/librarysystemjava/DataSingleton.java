package com.example.librarysystemjava;

import java.util.ArrayList;
import java.util.List;

public class DataSingleton {
    private static DataSingleton instance;
    private FileHandler fileHandler = new FileHandler();

    private List<String> allBooks = new ArrayList<>();
    private List<String> allStudents = new ArrayList<>();
    private List<String> allTransactions = new ArrayList<>();

    private DataSingleton() {}

    public static DataSingleton getInstance() {
        if (instance == null) instance = new DataSingleton();
        return instance;
    }

    public FileHandler getFileHandler() {return fileHandler;}

    public List<String> getAllBooks() {return allBooks;}
    public List<String> getAllStudents() {return allStudents;}
    public List<String> getAllTransactions() {return allTransactions;}
}
