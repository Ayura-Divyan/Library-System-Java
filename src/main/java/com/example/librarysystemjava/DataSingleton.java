package com.example.librarysystemjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DataSingleton {
    private static DataSingleton instance;
    private FileHandler fileHandler = new FileHandler();

    private ObservableList<String> allBooks = FXCollections.observableArrayList();
    private ObservableList<String> allStudents = FXCollections.observableArrayList();
    private ObservableList<String> allTransactions = FXCollections.observableArrayList();

    private java.io.File lastViewDirectory;

    private DataSingleton() {}

    public static DataSingleton getInstance() {
        if (instance == null) instance = new DataSingleton();
        return instance;
    }

    public FileHandler getFileHandler() {return fileHandler;}

    public ObservableList<String> getAllBooks() { return allBooks; }
    public ObservableList<String> getAllStudents() { return allStudents; }
    public ObservableList<String> getAllTransactions() { return allTransactions; }

    public java.io.File getLastViewDirectory() {return lastViewDirectory;}
    public void setLastViewDirectory(java.io.File dir) {this.lastViewDirectory = dir;}
}
