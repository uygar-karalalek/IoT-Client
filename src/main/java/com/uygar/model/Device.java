package com.uygar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Device {

    private String uuid;
    private String name;

    private String type;
    private List<Sensor> sensors;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Device(String uuid, String name, String type, List<Sensor> sensors, int userId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.sensors = sensors;
        this.userId = userId;
    }
}