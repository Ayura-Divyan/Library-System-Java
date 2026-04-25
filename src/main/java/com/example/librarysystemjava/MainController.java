package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainController {

    // Scene builder elements
    @FXML
    private Button btnImport;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnSearch;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        System.out.println("Launcher initialized");
    }

    @FXML
    public void onImportClicked() {
        System.out.println("Import button clicked"); // Console log

        // Actually loading the page
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("import-view.fxml"));
            javafx.scene.Node importView = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(importView);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Critical Error: Failed to load report-view.fxml. Is the file missing?", e);
        }
    }

    @FXML
    public void onReportClicked() {
        System.out.println("Report gen button clicked"); // Console log
        // Actually loading the page
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("report-view.fxml"));
            javafx.scene.Node reportView = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(reportView);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Critical Error: Failed to load report-view.fxml. Is the file missing?", e);
        }
    }

    @FXML
    public void onSearchClicked() {
        System.out.println("Search button clicked");
    }

}
