module com.example.librarysystemjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.librarysystemjava to javafx.fxml;
    exports com.example.librarysystemjava;
}