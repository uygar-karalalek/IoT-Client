package com.uygar.model.observable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObservableSensor {

    private SimpleStringProperty type;
    private SimpleDoubleProperty value;

    public ObservableSensor(String type, double value) {
        this.type = new SimpleStringProperty(type);
        this.value = new SimpleDoubleProperty(value);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }
}
