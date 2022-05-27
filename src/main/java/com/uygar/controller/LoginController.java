package com.uygar.controller;

import com.uygar.ParentControllerPair;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.uygar.Application.getParentControllerPair;

public class LoginController extends Controller {

    public TextField usernameField;
    public PasswordField passwordField;

    Stage loginStage;

    public void onLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        appProperties().putProperty("username", username);
        appProperties().putProperty("password", password);

        ParentControllerPair<Parent, HomeController> home = getParentControllerPair("home", "main.css");
        Stage homeStage = new Stage();
        home.getController().setHomeStage(homeStage);
        home.getController().setParentApplication(getParentApplication());
        Scene scene = new Scene(home.getParent());
        homeStage.setScene(scene);

        getParentApplication().updateUserId();
        if (getParentApplication().getUserId() <= 0) {
            passwordField.setStyle("-fx-background-color: #f3a4a4");
            usernameField.setStyle("-fx-background-color: #f3a4a4");
        }
        else {
            home.getController().init();
            loginStage.hide();
            homeStage.show();
        }
    }

    public void setLoginStage(Stage parentStage) {
        this.loginStage = parentStage;
    }
}

