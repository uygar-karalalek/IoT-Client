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
            getDevice("1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sensor> getDeviceSensors(String deviceUuid) {
        ArrayList<Sensor> sensors = new ArrayList<>();
        try {
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

    public void createDevice(Device device) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO device VALUES(?, ?, ?)");
            preparedStatement.setString(1, device.getUuid());
            preparedStatement.setString(2, device.getName());
            preparedStatement.setString(3, device.getType());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Device getDevice(String deviceUuid) {
        Device device = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM device WHERE uuid=" + deviceUuid);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                device = new Device(
                        set.getString("uuid"),
                        set.getString("name"),
                        set.getString("type"),
                        getDeviceSensors(deviceUuid)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return device;
    }

}