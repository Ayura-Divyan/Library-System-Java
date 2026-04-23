package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class ImportController {
    @FXML
    private ListView<String> invalidRecordsList;

    @FXML
    private TextField editField;

    private FileHandler fileHandler = new FileHandler();

    @FXML
    public void initialize() {
        invalidRecordsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                editField.setText(newValue);
            }
        });
    }

    @FXML
    public void onLoadClicked() {
        System.out.println("Loading CSV File...");

        String booksPath = "data/books.csv";
        String studentsPath = "data/students.csv";
        String transactionsPath = "data/transactions.csv";

        fileHandler.loadBooks(booksPath);
        fileHandler.loadStudents(studentsPath);
        fileHandler.loadTransactions(transactionsPath);


        invalidRecordsList.getItems().clear();
        invalidRecordsList.getItems().addAll(fileHandler.getInvalidBooks());
        invalidRecordsList.getItems().addAll(fileHandler.getInvalidStudents());
        invalidRecordsList.getItems().addAll(fileHandler.getInvalidTransactions());

        System.out.println("Files loaded! Invalid records found: " + invalidRecordsList.getItems().size());
    }

    @FXML
    public void onUpdateClicked() {
        String correctedRecord = editField.getText();
        System.out.println("Updating record: " + correctedRecord);
    }
}
