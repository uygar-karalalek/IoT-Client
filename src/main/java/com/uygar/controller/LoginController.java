package com.uygar.controller;

import com.uygar.App;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    public TextField usernameField;
    public PasswordField passwordField;

    public void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        App.applicationProperties.putProperty("username", username);
        App.applicationProperties.putProperty("password", password);


    }

}
