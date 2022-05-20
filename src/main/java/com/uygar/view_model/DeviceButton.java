package com.uygar.view_model;

import com.uygar.App;
import com.uygar.App.ParentControllerPair;
import com.uygar.controller.DeviceModifyController;
import com.uygar.model.observable.ObservableDevice;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringBootConfiguration;

import java.io.IOException;

import static com.uygar.App.getParentControllerPair;

public class DeviceButton extends Button {

    private ObservableDevice device;

    public DeviceButton(ObservableDevice obsDevice, FlowPane flow) {
        this.device = obsDevice;
        this.prefWidthProperty().bind(flow.widthProperty().divide(3.25));
        String imageName = obsDevice.getType().toLowerCase();
        Image image = new Image(App.class.getResourceAsStream("images/" + imageName + ".png"), 50.0, 50.0, true, true);
        this.setGraphic(new ImageView(image));
        this.getStyleClass().add("deviceButton");
        this.setPrefHeight(80);
        this.setUserData(new DeviceButtonData(obsDevice, false));
        this.setOnMouseClicked(this::onButtonClicked);
    }

    public void onButtonClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            if (!((DeviceButtonData) this.getUserData()).selected) {
                this.setStyle("-fx-background-color: blue;");
                ((DeviceButtonData) this.getUserData()).selected = true;
            } else {
                this.setStyle("");
                ((DeviceButtonData) this.getUserData()).selected = false;
            }
        }
        else enterDevice();
    }

    private void enterDevice() {
        Stage stage = new Stage();
        try {
            ParentControllerPair<GridPane, DeviceModifyController> deviceModify = getParentControllerPair(
                    "device_modify", "device_modify.css"
            );
            deviceModify.getController().device = this.device;
            deviceModify.getParent();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}