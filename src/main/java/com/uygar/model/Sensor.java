package com.uygar.model;

public class Sensor {

    private Integer id = null;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double value;

    public Sensor(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public Sensor(Integer id, String type, double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }
}