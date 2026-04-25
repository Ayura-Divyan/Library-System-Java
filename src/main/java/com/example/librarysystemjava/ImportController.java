package com.example.librarysystemjava;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ImportController {

    @FXML private TableView<String> booksTable, studentsTable, transactionsTable;
    @FXML private TableColumn<String, String> colBkId, colBkIsbn, colBkTitle, colBkCopies, colBkAvail, colBkPrice;
    @FXML private TableColumn<String, String> colStuId, colStuName;
    @FXML private TableColumn<String, String> colTrId, colTrDate, colTrBkId, colTrStuId, colTrType;
    @FXML private TextField editField;

    private FileHandler fileHandler = DataSingleton.getInstance().getFileHandler();
    private TableView<String> currentActiveTable;

    @FXML
    public void initialize() { // Controller startup
        setupColumn(colBkId, 0); setupColumn(colBkIsbn, 1); setupColumn(colBkTitle, 2);
        setupColumn(colBkCopies, 3); setupColumn(colBkAvail, 4); setupColumn(colBkPrice, 5);
        setupColumn(colStuId, 0); setupColumn(colStuName, 1);
        setupColumn(colTrId, 0); setupColumn(colTrDate, 1); setupColumn(colTrBkId, 2);
        setupColumn(colTrStuId, 3); setupColumn(colTrType, 4);

        DataSingleton dataSafe = DataSingleton.getInstance();
        booksTable.getItems().setAll(dataSafe.getAllBooks());
        studentsTable.getItems().setAll(dataSafe.getAllStudents());
        transactionsTable.getItems().setAll(dataSafe.getAllTransactions());

        fileHandler.syncData(dataSafe.getAllBooks(), dataSafe.getAllStudents(), dataSafe.getAllTransactions());

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
    public void onLoadClicked() { // Importing CSV files
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File lastDir = DataSingleton.getInstance().getLastViewDirectory();
        if (lastDir != null && lastDir.exists()) fileChooser.setInitialDirectory(lastDir);

        fileChooser.setTitle("Select Books File");
        File bookFile = fileChooser.showOpenDialog(null);

        if (bookFile != null) {
            DataSingleton.getInstance().setLastViewDirectory(bookFile.getParentFile());
            fileChooser.setInitialDirectory(bookFile.getParentFile());

            fileChooser.setTitle("Select Student File");
            File studentFile = fileChooser.showOpenDialog(null);
            fileChooser.setTitle("Select Transaction File");
            File transactionFile = fileChooser.showOpenDialog(null);

            if (studentFile != null && transactionFile != null) {
                populateTable(booksTable, bookFile);
                populateTable(studentsTable, studentFile);
                populateTable(transactionsTable, transactionFile);

                DataSingleton dataSafe = DataSingleton.getInstance();
                dataSafe.getAllBooks().setAll(booksTable.getItems());
                dataSafe.getAllStudents().setAll(studentsTable.getItems());
                dataSafe.getAllTransactions().setAll(transactionsTable.getItems());

                fileHandler.syncData(dataSafe.getAllBooks(), dataSafe.getAllStudents(), dataSafe.getAllTransactions());

                System.out.println("Data imported and synced successfully!");
            }
        }
    }

    @FXML
    public void onUpdateClicked() { // Updating the fields
        String correctedRecord = editField.getText();
        if (correctedRecord != null && currentActiveTable != null) {
            int selectedIndex = currentActiveTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                currentActiveTable.getItems().set(selectedIndex, correctedRecord);

                DataSingleton dataSafe = DataSingleton.getInstance();
                dataSafe.getAllBooks().setAll(booksTable.getItems());
                dataSafe.getAllStudents().setAll(studentsTable.getItems());
                dataSafe.getAllTransactions().setAll(transactionsTable.getItems());

                fileHandler.syncData(dataSafe.getAllBooks(), dataSafe.getAllStudents(), dataSafe.getAllTransactions());

                currentActiveTable.refresh();
                editField.clear();
            }
        }
    }

    @FXML
    public void onSaveClicked() { // Saving CSV files
        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Save Corrected CSVs");

        File lastDir = DataSingleton.getInstance().getLastViewDirectory();
        if (lastDir != null && lastDir.exists()) directoryChooser.setInitialDirectory(lastDir);

        File selectedDir = directoryChooser.showDialog(null);

        if (selectedDir != null) {
            DataSingleton data = DataSingleton.getInstance();
            fileHandler.saveAllData(selectedDir.getAbsolutePath(), data.getAllBooks(), data.getAllStudents(), data.getAllTransactions());
            System.out.println("Files saved successfully!");
        }
    }

    private void populateTable(TableView<String> tableView, File file) {
        tableView.getItems().clear();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (!lines.isEmpty()) lines.remove(0);
            tableView.getItems().addAll(lines);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void setupSelectionListener(TableView<String> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) { editField.setText(newV); currentActiveTable = tableView; }
        });
    }

    private void setupHighlighting(TableView<String> tableView, List<String> invalidList) {
        tableView.setRowFactory(tv -> new TableRow<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setStyle("");
                else if (invalidList.contains(item)) setStyle("-fx-background-color: #ffe6e6; -fx-text-inner-color: #cc0000;");
                else setStyle("-fx-background-color: #e6ffe6;");
            }
        });
    }
}