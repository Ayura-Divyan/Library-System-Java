module com.example.librarysystemjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarysystemjava to javafx.fxml;
    exports com.example.librarysystemjava;
}