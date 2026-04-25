package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ImportController {

    @FXML private ListView<String> booksListView;
    @FXML private ListView<String> studentsListView;
    @FXML private ListView<String> transactionsListView;
    @FXML private TextField editField;

    private FileHandler fileHandler = new FileHandler();
    private ListView<String> currentActiveList;

    @FXML
    public void initialize() {
        // selection logic
        setupSelectionListener(booksListView);
        setupSelectionListener(studentsListView);
        setupSelectionListener(transactionsListView);

        // Color highlighting logic
        setupHighlighting(booksListView, fileHandler.getInvalidBooks());
        setupHighlighting(studentsListView, fileHandler.getInvalidStudents());
        setupHighlighting(transactionsListView, fileHandler.getInvalidTransactions());
    }

    @FXML
    public void onLoadClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        fileChooser.setTitle("Select Books File");
        File bookFile = fileChooser.showOpenDialog(null);

        fileChooser.setTitle("Select Student File");
        File studentFile = fileChooser.showOpenDialog(null);

        fileChooser.setTitle("Select Transaction File");
        File transactionFile = fileChooser.showOpenDialog(null);

        if (bookFile != null && studentFile != null && transactionFile != null) {
            // Run them through FileHandler
            fileHandler.loadBooks(bookFile.getAbsolutePath());
            fileHandler.loadStudents(studentFile.getAbsolutePath());
            fileHandler.loadTransactions(transactionFile.getAbsolutePath());

            // Fill the tabs
            populateList(booksListView, bookFile);
            populateList(studentsListView, studentFile);
            populateList(transactionsListView, transactionFile);

            System.out.println("Data imported successfully!");
        }
    }

    @FXML
    public void onUpdateClicked() {
        String correctedRecord = editField.getText();
        if (correctedRecord != null && currentActiveList != null) {
            int selectedIndex = currentActiveList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                String oldRecord = currentActiveList.getItems().get(selectedIndex);

                List<String> activeInvalidList;
                if (currentActiveList == booksListView) activeInvalidList = fileHandler.getInvalidBooks();
                else if (currentActiveList == studentsListView) activeInvalidList = fileHandler.getInvalidStudents();
                else activeInvalidList = fileHandler.getInvalidTransactions();

                fileHandler.updateRecordValidation(oldRecord, correctedRecord, activeInvalidList);

                currentActiveList.getItems().set(selectedIndex, correctedRecord);
                currentActiveList.refresh(); // Forces the color to update
                editField.clear();
            }
        }
    }

    private void populateList(ListView<String> listView, File file) {
        listView.getItems().clear();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (!lines.isEmpty()) lines.remove(0); // drop header
            listView.getItems().addAll(lines);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void setupSelectionListener(ListView<String> listView) {
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                editField.setText(newV);
                currentActiveList = listView;
            }
        });
    }

    private void setupHighlighting(ListView<String> listView, List<String> invalidList) {
        listView.setCellFactory(lv -> new ListCell<String>() {
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