package com.example.librarysystemjava;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ImportController {

    @FXML
    private TableView<String> booksTable;
    @FXML
    private TableColumn<String, String> colBkId, colBkIsbn, colBkTitle, colBkCopies, colBkAvail, colBkPrice;

    @FXML
    private TableView<String> studentsTable;
    @FXML
    private TableColumn<String, String> colStuId, colStuName;

    @FXML
    private TableView<String> transactionsTable;
    @FXML
    private TableColumn<String, String> colTrId, colTrDate, colTrBkId, colTrStuId, colTrType;

    @FXML
    private TextField editField;

    private FileHandler fileHandler = DataSingleton.getInstance().getFileHandler();
    private TableView<String> currentActiveTable;

    @FXML
    public void initialize() {
        // 1. Bind the columns to split the CSV strings by commas
        setupColumn(colBkId, 0);
        setupColumn(colBkIsbn, 1);
        setupColumn(colBkTitle, 2);
        setupColumn(colBkCopies, 3);
        setupColumn(colBkAvail, 4);
        setupColumn(colBkPrice, 5);

        setupColumn(colStuId, 0);
        setupColumn(colStuName, 1);

        setupColumn(colTrId, 0);
        setupColumn(colTrDate, 1);
        setupColumn(colTrBkId, 2);
        setupColumn(colTrStuId, 3);
        setupColumn(colTrType, 4);

        // Loading singleton data
        DataSingleton dataSafe = DataSingleton.getInstance();
        booksTable.getItems().setAll(dataSafe.getAllBooks());
        studentsTable.getItems().setAll(dataSafe.getAllStudents());
        transactionsTable.getItems().setAll(dataSafe.getAllTransactions());

        // Invalid Selection and colour highlighting
        setupSelectionListener(booksTable);
        setupSelectionListener(studentsTable);
        setupSelectionListener(transactionsTable);

        setupHighlighting(booksTable, fileHandler.getInvalidBooks());
        setupHighlighting(studentsTable, fileHandler.getInvalidStudents());
        setupHighlighting(transactionsTable, fileHandler.getInvalidTransactions());
    }

    private void setupColumn(TableColumn<String, String> column, int index) {
        column.setCellValueFactory(data -> {
            String[] parts = data.getValue().split(",", -1);
            return new SimpleStringProperty(parts.length > index ? parts[index].trim() : "");
        });
    }

    @FXML
    public void onLoadClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // MEMORY: Open the popup in the last visited directory (if it exists)
        File lastDir = DataSingleton.getInstance().getLastViewDirectory();
        if (lastDir != null && lastDir.exists()) {
            fileChooser.setInitialDirectory(lastDir);
        }

        fileChooser.setTitle("Select Books File");
        File bookFile = fileChooser.showOpenDialog(null);

        if (bookFile != null) {
            // MEMORY: Save this folder so the next popups open here automatically!
            DataSingleton.getInstance().setLastViewDirectory(bookFile.getParentFile());
            fileChooser.setInitialDirectory(bookFile.getParentFile());

            fileChooser.setTitle("Select Student File");
            File studentFile = fileChooser.showOpenDialog(null);
            fileChooser.setTitle("Select Transaction File");
            File transactionFile = fileChooser.showOpenDialog(null);

            if (studentFile != null && transactionFile != null) {
                fileHandler.loadBooks(bookFile.getAbsolutePath());
                fileHandler.loadStudents(studentFile.getAbsolutePath());
                fileHandler.loadTransactions(transactionFile.getAbsolutePath());

                populateTable(booksTable, bookFile);
                populateTable(studentsTable, studentFile);
                populateTable(transactionsTable, transactionFile);

                DataSingleton dataSafe = DataSingleton.getInstance();
                dataSafe.getAllBooks().setAll(booksTable.getItems());
                dataSafe.getAllStudents().setAll(studentsTable.getItems());
                dataSafe.getAllTransactions().setAll(transactionsTable.getItems());

                System.out.println("Data imported into tables successfully!");
            }
        }
    }

    @FXML
    public void onUpdateClicked() {
        String correctedRecord = editField.getText();
        if (correctedRecord != null && currentActiveTable != null) {
            int selectedIndex = currentActiveTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                String oldRecord = currentActiveTable.getItems().get(selectedIndex);

                List<String> activeInvalidList;
                if (currentActiveTable == booksTable) activeInvalidList = fileHandler.getInvalidBooks();
                else if (currentActiveTable == studentsTable) activeInvalidList = fileHandler.getInvalidStudents();
                else activeInvalidList = fileHandler.getInvalidTransactions();

                fileHandler.updateRecordValidation(oldRecord, correctedRecord, activeInvalidList);
                currentActiveTable.getItems().set(selectedIndex, correctedRecord);

                // Update the safe
                DataSingleton dataSafe = DataSingleton.getInstance();
                dataSafe.getAllBooks().setAll(booksTable.getItems());
                dataSafe.getAllStudents().setAll(studentsTable.getItems());
                dataSafe.getAllTransactions().setAll(transactionsTable.getItems());

                currentActiveTable.refresh();
                editField.clear();
            }
        }
    }

    @FXML
    public void onSaveClicked() {
        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Save Files");

        File lastDir = DataSingleton.getInstance().getLastViewDirectory();
        if (lastDir != null && lastDir.exists()) {
            directoryChooser.setInitialDirectory(lastDir);
        }

        File selectedDir = directoryChooser.showDialog(null);

        if (selectedDir != null) {
            DataSingleton data = DataSingleton.getInstance();
            fileHandler.saveAllData(
                    selectedDir.getAbsolutePath(),
                    data.getAllBooks(),
                    data.getAllBooks(),
                    data.getAllTransactions()
            );
            System.out.println("All files saved successfully to: " + selectedDir.getAbsolutePath());
        }
    }

    private void populateTable(TableView<String> tableView, File file) {
        tableView.getItems().clear();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (!lines.isEmpty()) lines.remove(0); // Remove header
            tableView.getItems().addAll(lines);
        } catch (IOException e) {
            throw new RuntimeException("Critical Error: Failed to load report-view.fxml", e);
        }
    }

    private void setupSelectionListener(TableView<String> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                editField.setText(newV);
                currentActiveTable = tableView;
            }
        });
    }

    private void setupHighlighting(TableView<String> tableView, List<String> invalidList) {
        tableView.setRowFactory(tv -> new TableRow<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (invalidList.contains(item)) {
                        setStyle("-fx-background-color: #ffe6e6; -fx-text-fill: #cc0000; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-background-color: #e6ffe6; -fx-text-fill: #006600;");
                    }
                }
            }
        });
    }
}