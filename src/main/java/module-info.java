module com.uygar {
    requires javafx.controls;
    requires javafx.fxml;
    requires http.request;

    opens com.uygar to javafx.fxml;
    exports com.uygar;
    exports com.uygar.properties;
    exports com.uygar.controller;
}