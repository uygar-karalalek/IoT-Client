package com.uygar.controller;

import com.uygar.Application;
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
        this.sensorType.setText(observableSensor.getType());
        this.slider.setValue(observableSensor.getValue());
        this.value.textProperty().bind(slider.valueProperty().asString().concat(" " + getTypeUnit(observableSensor.getType())));
        this.slider.valueProperty().addListener(this::onSliderValueChanged);
    }

    public void onSliderValueChanged(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
        new Thread(() -> getDeviceSensorMySqlRepository().updateSensor(observableSensor.getId(), (Double) newVal)).start();
    }

    public String getTypeUnit(String type) {
        if (type.toLowerCase().contains("co2"))
            return "ppm";
        else if (type.toLowerCase().contains("light"))
            return "lux";
        else if (type.contains("humidity"))
            return "g/m3";
        return "unknown";
    }

}