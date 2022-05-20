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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(System.out::println);
                }
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
    }

    public void inputChanged(ActionEvent event) {
    }

}