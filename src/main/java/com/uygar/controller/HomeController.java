package com.uygar.controller;

import com.uygar.ParentControllerPair;
import com.uygar.model.observable.ObservableDevice;
import com.uygar.view_model.DeviceButton;
import com.uygar.view_model.DeviceButtonData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import static com.uygar.Application.getParentControllerPair;

public class HomeController extends Controller {

    @FXML
    public TextField searchField;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public FlowPane flow;
    @FXML
    public Button logoutBtn;

    ObservableList<ObservableDevice> devices = FXCollections.observableArrayList();

    Stage homeStage;

    public void init() {
        searchField.setOnAction(this::inputChanged);

        logoutBtn.setOnMouseClicked(mouseEvent -> {
            try {
                ParentControllerPair<Parent, LoginController> login = getParentControllerPair("login", "login.css");
                Stage stage = new Stage();
                login.getController().setParentApplication(getParentApplication());
                login.getController().setLoginStage(stage);
                Scene scene = new Scene(login.getParent(), 450, 400);
                stage.setScene(scene);
                stage.show();
                homeStage.hide();
                getParentApplication().getAppProps().putProperty("username", "");
                getParentApplication().getAppProps().putProperty("password", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        devices.addListener((ListChangeListener<? super ObservableDevice>) change -> {
            while (change.next()) if (change.wasAdded()) change.getAddedSubList().forEach(observableDevice ->
                    flow.getChildren().add(new DeviceButton(observableDevice, flow, getParentApplication())));
        });

        deviceRepository().getDevices(getUserId()).forEach(device -> {
            ObservableDevice observableDevice = new ObservableDevice(
                    device.getUuid(),
                    device.getName(),
                    device.getType()
            );
            devices.add(observableDevice);
        });
    }

    public void setHomeStage(Stage homeStage) {
        this.homeStage = homeStage;
    }

    @FXML
    public void onAddDevice() {
        try {
            ParentControllerPair<Parent, DeviceAddController>
                    parentControllerPair = getParentControllerPair("device_add", "device_add.css");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parentControllerPair.getParent()));

            parentControllerPair.getController().setStage(stage);
            parentControllerPair.getController().setDevices(devices);
            parentControllerPair.getController().setParentApplication(getParentApplication());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemoveSelected() {
        flow.getChildren().removeIf(node -> {
            // First I want to make sure this is a button
            boolean isButtonAndHasDataAssociated = (node instanceof Button) && node.getUserData() != null;
            if (isButtonAndHasDataAssociated) {
                DeviceButtonData deviceButtonData = (DeviceButtonData) node.getUserData();
                if (deviceButtonData.selected) {
                    deviceRepository().removeByUuid(deviceButtonData.device.getUuid());
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