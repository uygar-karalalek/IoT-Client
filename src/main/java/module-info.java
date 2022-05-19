module com.uygar {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.uygar to javafx.fxml;
    exports com.uygar;
    exports com.uygar.controller;
}