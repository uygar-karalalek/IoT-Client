package com.uygar.repo;

import com.uygar.model.Device;
import com.uygar.model.Sensor;

import java.sql.*;
import java.util.ArrayList;

public class DeviceRepository {

    private Connection connection;

    public DeviceRepository() {
        try {
            String database_dir = System.getProperty("database_dir");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + database_dir);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sensor> getDeviceSensors(int userId, String key, String value) {
        ArrayList<Sensor> sensors = new ArrayList<>();
        try {
            PreparedStatement deviceUuidStmt = connection.prepareStatement("SELECT uuid FROM device WHERE user_id=? AND " + key + "=?");
            deviceUuidStmt.setInt(1, userId);
            deviceUuidStmt.setString(2,value);

            String deviceUuid = "";
            ResultSet stmtGetUuid = deviceUuidStmt.executeQuery();

            if (stmtGetUuid.next())
                deviceUuid = stmtGetUuid.getString("uuid");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sensor WHERE device_uuid=?");
            preparedStatement.setString(1, deviceUuid);

            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Sensor sensor = new Sensor(set.getString("type"), set.getDouble("value"));
                sensors.add(sensor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensors;
    }

    public void createDevice(Device device, int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO device VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, device.getUuid());
            preparedStatement.setString(2, device.getName());
            preparedStatement.setString(3, device.getType());
            preparedStatement.setInt(4, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Device> getDevices(int userId) {
        ArrayList<Device> devices = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM device WHERE user_id=?");
            preparedStatement.setInt(1, userId);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Device device = new Device(
                        set.getString("uuid"),
                        set.getString("name"),
                        set.getString("type"),
                        getDeviceSensors(userId, "uuid", set.getString("uuid")),
                        set.getInt("user_id")
                );
                devices.add(device);
            }
            return devices;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return devices;
    }

    public void removeDevice(String uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM device WHERE uuid=?");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Device getDevice(String deviceUuid, int userId) {
        Device device = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM device WHERE uuid=? AND user_id=?");
            preparedStatement.setString(1, deviceUuid);
            preparedStatement.setInt(2, userId);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                device = new Device(
                        set.getString("uuid"),
                        set.getString("name"),
                        set.getString("type"),
                        getDeviceSensors(userId, "uuid", deviceUuid),
                        set.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return device;
    }

}