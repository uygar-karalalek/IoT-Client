package com.uygar.controller;

import com.uygar.model.SensorType;
import com.uygar.model.observable.ObservableSensor;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class DeviceSensorController extends Controller {

    public Slider slider;
    public Label value;
    public Label sensorType;
    public String deviceUuid = "";
    ObservableSensor observableSensor;

    public void setObservableSensor(ObservableSensor observableSensor) {
        this.observableSensor = observableSensor;
        this.sensorType.setText(observableSensor.getRawType());
        this.slider.setValue(observableSensor.getValue());
        this.slider.valueProperty().addListener((obs, oldVal, newVal) ->
                value.setText(Math.round(newVal.floatValue()) + " " + getTypeUnit(observableSensor.getType())));
        this.slider.valueProperty().addListener(this::onSliderValueChanged);
    }

    public void onSliderValueChanged(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
        new Thread(() -> getDeviceSensorMySqlRepository().updateSensor(observableSensor.getId(), (Double) newVal)).start();
    }

    public String getTypeUnit(SensorType type) {
        return type.getUnit();
    }

}