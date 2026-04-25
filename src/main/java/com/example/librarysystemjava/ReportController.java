package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
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

    private FileHandler fileHandler = DataSingleton.getInstance().getFileHandler();


    @FXML
    public void initialize() {
        System.out.println("Report Screen loaded"); // Console log

        colTransId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void onCalculateAverageClicked() {
        System.out.println("Calculating average cost...");

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

        String selectedDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Generating report for: " + selectedDate);

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
