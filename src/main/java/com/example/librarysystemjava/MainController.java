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
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("import-controller.fxml"));
            javafx.scene.Node importView = fxmlLoader.loader();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(importView);
        } catch (java.io.IOException e) {
            System.out.println("Error: loading import view: " +  e.getMessage());
            e.printStackTrace();
        }
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
