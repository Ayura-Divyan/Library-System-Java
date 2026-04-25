package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.util.List;

public class ReportController {
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblAverageCost;

    @FXML
    private TableView<Transaction> reportTable;

    @FXML
    private TableColumn<Transaction, String> colTransId;

    @FXML
    private TableColumn<Transaction, String> colBookId;

    @FXML
    private TableColumn<Transaction, String> colStudentId;

    @FXML
    private TableColumn<Transaction, String> colDate;

    private FileHandler fileHandler = new FileHandler();


    @FXML
    public void initialize() {
        System.out.println("Report Screen loaded");

        colTransId.setCellValueFactory(new PropertyValueFactory<>("transactioId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void onCalculateAverageClicked() {
        System.out.println("Calculating average cost...");

        fileHandler.loadBooks("../data/book.csv");
        List<Book> books = fileHandler.getValidBooks();

        if (books.isEmpty()) {
            lblAverageCost.setText("No books found");
            return;
        }

        double totalCost = 0.0;
        for (Book book : books) {
            totalCost += book.getPrice();
        }

        double averageCost = totalCost / books.size();

        lblAverageCost.setText(String.format("%.2f", averageCost));
    }

    @FXML
    public void onGenerateReportClicked() {
        if (datePicker.getValue() == null) {
            System.out.println("Please select a date first");
            return;
        }

        String selectedDate = datePicker.getValue().toString();
        System.out.println("Generating report for: " + selectedDate);

        fileHandler.loadTransactions("../data/transactions.csv");
        List<Transaction> allTransactions = fileHandler.getValidTransactions();

        reportTable.getItems().clear();

        for (Transaction transaction : allTransactions) {
            if (transaction.getDate().equals(selectedDate) && transaction.getType() == 1) {
                reportTable.getItems().add(transaction);
            }
        }

        System.out.println("Found " + reportTable.getItems().size() + " records");
    }
}
