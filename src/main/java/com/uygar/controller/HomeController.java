package com.uygar.controller;

import com.uygar.App;
import com.uygar.App.ParentControllerPair;
import com.uygar.model.observable.ObservableDevice;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.uygar.App.deviceRepository;
import static com.uygar.App.getParentControllerPair;

public class HomeController implements Initializable {

    @FXML
    public TextField searchField;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public FlowPane flow;

    ObservableList<ObservableDevice> devices = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setOnAction(this::inputChanged);

        devices.addListener((ListChangeListener<? super ObservableDevice>) change -> {
            while (change.next()) {
                if (change.wasAdded())
                    change.getAddedSubList().forEach(observableDevice
                            -> flow.getChildren().add(buildDeviceButton(observableDevice)));
            }
        });

        App.deviceRepository.getDevices().forEach(device -> {
            ObservableDevice observableDevice = new ObservableDevice(
                    device.getUuid(),
                    device.getName(),
                    device.getType()
            );
            devices.add(observableDevice);
        });
    }

    class DeviceButtonData {
        ObservableDevice device;
        boolean selected;

        public DeviceButtonData(ObservableDevice device, boolean selected) {
            this.device = device;
            this.selected = selected;
        }
    }

    private Button buildDeviceButton(ObservableDevice observableDevice) {
        Button button = new Button(observableDevice.getName());
        button.prefWidthProperty().bind(flow.widthProperty().divide(3.25));
        String imageName = observableDevice.getType().toLowerCase();
        Image image = new Image(App.class.getResourceAsStream("images/" + imageName + ".png"), 50.0, 50.0, true, true);
        button.setGraphic(new ImageView(image));
        button.getStyleClass().add("deviceButton");
        button.setPrefHeight(80);
        button.setUserData(new DeviceButtonData(observableDevice, false));

        button.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (!((DeviceButtonData) button.getUserData()).selected) {
                    button.setStyle("-fx-background-color: blue;");
                    ((DeviceButtonData) button.getUserData()).selected = true;
                } else {
                    button.setStyle("");
                    ((DeviceButtonData) button.getUserData()).selected = false;
                }
            } else {
                System.out.println("entering ");
            }
        });
        return button;
    }

    @FXML
    public void onAddDevice() {
        try {
            ParentControllerPair<Parent, DeviceAddController> parentControllerPair =
                    getParentControllerPair("device_add", "device_add.css");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parentControllerPair.getParent()));

            parentControllerPair.getController().setStage(stage);
            parentControllerPair.getController().setDevices(devices);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemoveSelected() {
        flow.getChildren().removeIf(node -> {
            boolean isButtonAndHasDataAssociated = (node instanceof Button) && node.getUserData() != null;
            if (isButtonAndHasDataAssociated) {
                DeviceButtonData deviceButtonData = (DeviceButtonData) node.getUserData();
                if (deviceButtonData.selected) {
                    deviceRepository.removeDevice(deviceButtonData.device.getUuid());
                    devices.remove(deviceButtonData.device);
                    return true;
                }
            }
            return false;
        });
    }

    public void inputChanged(ActionEvent event) {
    }

}