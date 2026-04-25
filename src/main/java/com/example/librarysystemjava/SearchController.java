package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SearchController {

    // Fields
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Book> searchTable;
    @FXML
    private TableColumn<Book, String> colBookId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colCopies;
    @FXML
    private TableColumn<Book, String> colAvailability;
    @FXML
    private TableColumn<Book, String> colPrice;

    private FileHandler fileHandler = DataSingleton.getInstance().getFileHandler(); // Get file data

    @FXML
    public void initialize() {
        System.out.println("Search Screen Loaded");

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void onSearchClicked() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            System.out.println("Search Query Empty");
            return;
        }

        List<Book> allBooks = fileHandler.getValidBooks();
        searchTable.getItems().clear();

        boolean isWildCard = query.endsWith("*");
        String cleanQuery = isWildCard ? query.substring(0, query.length() - 1) : query;

        for (Book book : allBooks) {
            String bookTitle = book.getTitle().toLowerCase();

            if (isWildCard) {
                if (bookTitle.startsWith(query)) {
                    searchTable.getItems().add(book);
                }
            } else {
                if (bookTitle.equals(cleanQuery)) {
                    searchTable.getItems().add(book);
                }
            }
        }
        System.out.println("Search Table Loaded\n\"Found \" + searchTable.getItems().size() + \" matching books.\"");
    }

    @FXML
    public void onExportClicked() {
        if (searchTable.getItems().isEmpty()) {
            System.out.println("Nothing to export");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Search Results");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        fileChooser.setInitialFileName("book_search_results.txt");

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("--- Library System Book Search Results ---");
                writer.println(String.format("%-10s | %-30s | %-6s | %-12s | %-10s", "Book ID", "Title", "Copies", "Availability", "Price"));
                writer.println("----------------------------------------------------------------------------------");

                for (Book book : searchTable.getItems()) {
                    writer.println(String.format("%-10s | %-30s | %-6d | %-12d | £%.2f",
                            book.getBookId(), book.getTitle(), book.getCopies(), book.getAvailability(), book.getPrice()));
                }

                System.out.println("Results successfully exported to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error writing to file" + e.getMessage());
            }
        }
    }
}
