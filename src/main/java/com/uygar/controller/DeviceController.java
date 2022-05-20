package com.uygar.controller;

import com.uygar.App;
import com.uygar.model.Device;
import com.uygar.model.observable.ObservableDevice;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.UUID;

public class DeviceController {

    public TextField nameField;
    public TextField typeField;

    private Stage stage;

    ObservableList<ObservableDevice> devices;

    public void onCreateDevice() {
        Device device = new Device(UUID.randomUUID().toString(), nameField.getText(), typeField.getText(), new ArrayList<>());
        App.deviceRepository.createDevice(device);
        devices.add(new ObservableDevice(device.getUuid(), device.getName(), device.getType()));
        stage.hide();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDevices(ObservableList<ObservableDevice> devices) {
        this.devices = devices;
    }

}