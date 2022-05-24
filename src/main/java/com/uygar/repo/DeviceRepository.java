package com.uygar.repo;

import com.uygar.model.Device;
import com.uygar.model.Sensor;

import java.util.ArrayList;

public interface DeviceRepository {

    ArrayList<Sensor> getDeviceSensors(int userId, String key, String value);
    void createDevice(Device device, int userId);
    int createSensor(Sensor sensor, String devUuid);
    ArrayList<Device> getDevices(int userId);
    void removeDevice(String uuid);
    Device getDevice(String deviceUuid, int userId);
    void updateSensor(Integer id, Double newVal);

}