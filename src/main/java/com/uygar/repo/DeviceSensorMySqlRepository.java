package com.uygar.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeviceSensorMySqlRepository implements DeviceSensorRepository, Repository {

    public void updateSensor(Integer id, Double newVal) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE sensor SET value=? WHERE id=?");
            preparedStatement.setDouble(1, newVal);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}