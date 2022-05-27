package com.uygar.controller;

import com.uygar.model.SensorType;
import com.uygar.model.observable.ObservableSensor;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class DeviceSensorController extends Controller {

    public VBox root;
    public Slider slider;
    public Label value;
    public Label sensorType;
    public String deviceUuid = "";

    ObservableSensor observableSensor;
    public ObservableList<ObservableSensor> sensors;

    public void setObservableSensor(ObservableSensor observableSensor) {
        this.observableSensor = observableSensor;
        this.sensorType.setText(observableSensor.getRawType());
        this.value.setText(Math.round(observableSensor.getValue()) + " " + getTypeUnit(observableSensor.getType()));
        this.slider.setValue(observableSensor.getValue());
        this.slider.valueProperty().addListener((obs, oldVal, newVal) ->
                value.setText(Math.round(newVal.floatValue()) + " " + getTypeUnit(observableSensor.getType())));
        this.slider.valueProperty().addListener(this::onSliderValueChanged);

        this.root.setUserData(this.observableSensor.getId());
    }

    public void onSliderValueChanged(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
        new Thread(() -> getDeviceSensorMySqlRepository().updateSensor(observableSensor.getId(), (Double) newVal)).start();
    }

    public String getTypeUnit(SensorType type) {
        return type.getUnit();
    }

    public void onSensorRemove() {
        getParentApplication().getDeviceSensorRepository().removeSensor(observableSensor.getId());
        sensors.removeIf(s -> s.getId() == observableSensor.getId());
    }

    public void setSensors(ObservableList<ObservableSensor> sensors) {
        this.sensors = sensors;
    }
}