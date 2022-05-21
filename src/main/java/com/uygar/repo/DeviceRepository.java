package com.uygar.repo;

import com.uygar.model.Device;
import com.uygar.model.Sensor;

import java.sql.*;
import java.util.ArrayList;

public class DeviceRepository {

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:4444/iot-client", "root", "iot-password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Sensor> getDeviceSensors(int userId, String key, String value) {
        ArrayList<Sensor> sensors = new ArrayList<>();
        try {
            PreparedStatement deviceUuidStmt = getConnection().prepareStatement("SELECT uuid FROM device WHERE user_id=? AND " + key + "=?");
            deviceUuidStmt.setInt(1, userId);
            deviceUuidStmt.setString(2,value);

            String deviceUuid = "";
            ResultSet stmtGetUuid = deviceUuidStmt.executeQuery();

            if (stmtGetUuid.next())
                deviceUuid = stmtGetUuid.getString("uuid");

            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM sensor WHERE device_uuid=?");
            preparedStatement.setString(1, deviceUuid);

            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                Sensor sensor = new Sensor(set.getInt("id"), set.getString("type"), set.getDouble("value"));
                sensors.add(sensor);
            }
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensors;
    }

    public void createDevice(Device device, int userId) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO device VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, device.getUuid());
            preparedStatement.setString(2, device.getName());
            preparedStatement.setString(3, device.getType());
            preparedStatement.setInt(4, userId);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSensor(Sensor sensor, String devUuid) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sensor VALUES(?, ?, ?, ?)");
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setString(2, sensor.getType());
            preparedStatement.setDouble(3, sensor.getValue());
            preparedStatement.setString(4, devUuid);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Device> getDevices(int userId) {
        ArrayList<Device> devices = new ArrayList<>();
        try {
            Connection connection = getConnection();
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
            connection.close();
            return devices;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return devices;
    }

    public void removeDevice(String uuid) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM device WHERE uuid=?");
            preparedStatement.setString(1, uuid);
            connection.close();
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Device getDevice(String deviceUuid, int userId) {
        Device device = null;
        try {
            Connection connection = getConnection();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return device;
    }

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