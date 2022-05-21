package com.uygar.controller;

import com.uygar.App;
import com.uygar.ParentControllerPair;
import com.uygar.model.Sensor;
import com.uygar.model.observable.ObservableDevice;
import com.uygar.model.observable.ObservableSensor;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DeviceModifyController {


    public GridPane root;
    public ScrollPane sensorsScroll;
    public FlowPane sensorsFlow;
    public VBox add;
    public ChoiceBox<String> typeBox;
    public Slider slider;

    public ObservableDevice device;
    public ObservableList<ObservableSensor> sensors = FXCollections.observableArrayList();
    public Label title;

    public void setDevice(ObservableDevice device) {
        this.device = device;
        this.title.setText("Modify the device: " + device.getName());

        this.sensors.addListener((ListChangeListener<? super ObservableSensor>) change -> {
            while (change.next()) {
                change.getAddedSubList().forEach(sensorObservable -> {
                    try {
                        ParentControllerPair<Parent, DeviceSensorController> deviceSensor = App.getParentControllerPair("device_sensor", "device_sensor.css");
                        deviceSensor.getController().deviceUuid = device.getUuid();
                        deviceSensor.getController().setObservableSensor(sensorObservable);
                        sensorsFlow.getChildren().add(deviceSensor.getParent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        App.deviceRepository.getDeviceSensors(App.userId, "name", device.getName()).forEach(sensor -> this.sensors.add(
                new ObservableSensor(sensor.getId(), sensor.getType(), sensor.getValue())));
    }

    public void onCreate() {

        String type = "";
        if (typeBox.getValue().toLowerCase().contains("co2"))
            type = "CO2";
        else if (typeBox.getValue().toLowerCase().contains("light"))
            type = "light";
        else if (typeBox.getValue().toLowerCase().contains("humidity"))
            type = "humidity";

        Sensor deviceSensor = new Sensor(type, slider.getValue());
        App.deviceRepository.createSensor(deviceSensor, device.getUuid());

        sensors.add(new ObservableSensor(deviceSensor.getId(), type, deviceSensor.getValue()));
    }
}