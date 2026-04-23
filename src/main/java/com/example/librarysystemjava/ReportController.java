package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.event.ActionEvent;

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

    @FXML
    public void initialize() {
        System.out.println("Report Screen loaded");
    }

    @FXML
    void onCalculateAverageClicked(ActionEvent event) {
        System.out.println("Calculating average cost...");
    }

    @FXML
    void onGenerateReportClicked(ActionEvent event) {
        System.out.println("Generating report for selec     ted date...");
    }
}
