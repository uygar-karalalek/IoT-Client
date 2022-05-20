package com.uygar.model;

public class SensorType {

    private String type, unit;

    public SensorType() {
    }

    public SensorType(String type, String unit) {
        this.type = type;
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}