package com.uygar.controller;

import com.uygar.Application;
import com.uygar.ParentControllerPair;
import com.uygar.model.Sensor;
import com.uygar.model.SensorType;
import com.uygar.model.observable.ObservableDevice;
import com.uygar.model.observable.ObservableSensor;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeviceModifyController extends Controller implements Initializable {

    @FXML
    public GridPane root;
    @FXML
    public ScrollPane sensorsScroll;
    @FXML
    public FlowPane sensorsFlow;
    @FXML
    public VBox add;
    @FXML
    public ChoiceBox<SensorType> typeBox;
    @FXML
    public Slider slider;

    public ObservableDevice device;
    public ObservableList<ObservableSensor> sensors = FXCollections.observableArrayList();
    public Label title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeBox.getItems().addAll(SensorType.getKnownSensors());
        typeBox.getSelectionModel().selectFirst();
        typeBox.setConverter(new StringConverter<>() {
            @Override
            public SensorType fromString(String s) {
                return SensorType.valueOf(s);
            }

            @Override
            public String toString(SensorType sensorType) {
                return sensorType == null ? SensorType.UNKNOWN.toString() : sensorType.toString();
            }
        });
    }

    public void setDevice(ObservableDevice device) {
        this.device = device;
        this.title.setText("Modify the device: " + device.getName());

        this.sensors.addListener((ListChangeListener<? super ObservableSensor>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(observableSensor -> sensorsFlow.getChildren()
                            .removeIf(node -> node.getUserData() != null && (Integer) node.getUserData() == observableSensor.getId()));
                }
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(sensorObservable -> {
                        try {
                            ParentControllerPair<Parent, DeviceSensorController> deviceSensor = Application.getParentControllerPair("device_sensor", "device_sensor.css");
                            deviceSensor.getController().setParentApplication(getParentApplication());
                            deviceSensor.getController().deviceUuid = device.getUuid();
                            deviceSensor.getController().setSensors(sensors);
                            deviceSensor.getController().setObservableSensor(sensorObservable);
                            sensorsFlow.getChildren().add(deviceSensor.getParent());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        deviceRepository().getDeviceSensors(getUserId(), "name", device.getName()).forEach(sensor -> this.sensors.add(
                new ObservableSensor(sensor.getId(), sensor.getType(), sensor.getValue())));
    }

    public void onCreate() {
        Sensor deviceSensor = new Sensor(typeBox.getValue().getType(), slider.getValue());
        int sensorId = deviceRepository().createSensor(deviceSensor, device.getUuid());

        sensors.add(new ObservableSensor(sensorId, typeBox.getValue().getType(), deviceSensor.getValue()));
    }
}