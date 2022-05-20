module com.uygar {
    requires javafx.controls;
    requires javafx.fxml;
    requires http.request;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens com.uygar to javafx.fxml;
    exports com.uygar;
    exports com.uygar.properties;
    exports com.uygar.controller;
}