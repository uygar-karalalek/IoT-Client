package com.uygar.model;

import java.util.Arrays;

public enum SensorType {

    CO2("CO2", "ppm"),
    LIGHT("light", "lux"),
    HUMIDITY("humidity", "g/m3"),
    UNKNOWN("unknown", "unknown");

    private String type, unit;

    SensorType(String type, String unit) {
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

    public static SensorType[] getKnownSensors() {
        SensorType[] values = values();
        SensorType[] toReturn = new SensorType[values.length - 1];
        for (int i = 0, j = 0; i < toReturn.length; i++, j++) {
            if (values[i] == UNKNOWN) j++;
            toReturn[i] = values[j];
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return type + " (" + unit + ')';
    }
}