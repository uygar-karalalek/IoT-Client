package com.uygar.controller;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller {

    public TextField usernameField;
    public PasswordField passwordField;

    public void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        appProperties().putProperty("username", username);
        appProperties().putProperty("password", password);

    }

}
