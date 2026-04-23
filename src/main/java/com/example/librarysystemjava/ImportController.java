package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;

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
        System.out.println("Opening File Chooser...");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Select Book CSV file
        fileChooser.setTitle("Select Books File");
        File studentFile = fileChooser.showOpenDialog(null);

        // Select Student CSV file
        fileChooser.setTitle("Select Student File");
        File bookFile = fileChooser.showOpenDialog(null);

        //Select Transaction CSV file
        fileChooser.setTitle("Select Transaction File");
        File transactionFile = fileChooser.showOpenDialog(null);

        if (bookFile != null && studentFile != null && transactionFile != null) {
            fileHandler.loadBooks(bookFile.getAbsolutePath());
            fileHandler.loadStudents(studentFile.getAbsolutePath());
            fileHandler.loadTransactions(transactionFile.getAbsolutePath());


            invalidRecordsList.getItems().clear();
            invalidRecordsList.getItems().addAll(fileHandler.getInvalidBooks());
            invalidRecordsList.getItems().addAll(fileHandler.getInvalidStudents());
            invalidRecordsList.getItems().addAll(fileHandler.getInvalidTransactions());

            System.out.println("Files loaded! Invalid records found: " + invalidRecordsList.getItems().size());
        } else {
            System.out.println("Import cancelled. You must select all three files");
        }
    }

    @FXML
    public void onUpdateClicked() {
        String correctedRecord = editField.getText();
        System.out.println("Updating record: " + correctedRecord);
    }
}
