package com.uygar.controller;

import com.uygar.App;
import com.uygar.model.observable.ObservableSensor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class DeviceSensorController {

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
        this.slider.valueProperty().addListener((observableValue, oldVal, newVal) -> new Thread(() -> App.deviceRepository.updateSensor(observableSensor.getId(), (Double) newVal)).start());
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