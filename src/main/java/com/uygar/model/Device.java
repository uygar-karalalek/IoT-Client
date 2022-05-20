package com.uygar.model;

import java.util.ArrayList;
import java.util.UUID;

public class Device {

    private String uuid;
    private String name;

    private String type;
    private ArrayList<Sensor> sensors;

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

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Device(String uuid, String name, String type, ArrayList<Sensor> sensors) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.sensors = sensors;
    }
}