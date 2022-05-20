package com.uygar.model.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableDevice {

    private SimpleStringProperty uuid, name, type;
    private ObservableList<ObservableSensor> sensors;

    public ObservableDevice(String uuid, String name, String type) {
        this.uuid = new SimpleStringProperty(uuid);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.sensors = FXCollections.observableArrayList();
    }

    public String getUuid() {
        return uuid.get();
    }

    public SimpleStringProperty uuidProperty() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid.set(uuid);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public ObservableList<ObservableSensor> getSensors() {
        return sensors;
    }

    public void setSensors(ObservableList<ObservableSensor> sensors) {
        this.sensors = sensors;
    }
}