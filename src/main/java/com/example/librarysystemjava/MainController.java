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
        System.out.println("Import button clicked");
    }

    @FXML
    public void onReportClicked() {
        System.out.println("Export button clicked");
    }

    @FXML
    public void onSearchClicked() {
        System.out.println("Search button clicked");
    }

}
