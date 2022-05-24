package com.uygar.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Repository {

    default Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:4444/iot-client", "root", "iot-password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}