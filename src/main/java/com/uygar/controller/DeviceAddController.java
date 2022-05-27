package com.uygar.controller;

import com.uygar.model.Device;
import com.uygar.model.DeviceType;
import com.uygar.model.observable.ObservableDevice;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class DeviceAddController extends Controller implements Initializable {

    public TextField nameField;
    public ChoiceBox<DeviceType> typeBox;
    public Button createBtn;

    private Stage stage;

    ObservableList<ObservableDevice> devices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeBox.getItems().setAll(DeviceType.values());
        typeBox.getSelectionModel().selectFirst();
        typeBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(DeviceType deviceType) {
                return deviceType.getName();
            }

            @Override
            public DeviceType fromString(String deviceName) {
                return DeviceType.fromName(deviceName);
            }
        });
        createBtn.setDisable(true);

        nameField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            boolean deviceExists = devices.stream().anyMatch(device -> newVal.equalsIgnoreCase(device.getName()));
            if (newVal.isBlank() || deviceExists) createBtn.setDisable(true);
            else if (createBtn.isDisable()) createBtn.setDisable(false);
        });
    }

    public void onCreateDevice() {
        Device device = new Device(UUID.randomUUID().toString(), nameField.getText(),
                typeBox.getValue().getName(), new ArrayList<>(), getUserId());
        deviceRepository().createDevice(device, getUserId());
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