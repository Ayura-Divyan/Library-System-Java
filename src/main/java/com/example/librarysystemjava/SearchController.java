package com.example.librarysystemjava;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

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

}
