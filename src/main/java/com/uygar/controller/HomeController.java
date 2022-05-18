package com.uygar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    public TextField searchField;
    @FXML
    public ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setOnAction(this::inputChanged);

    }

    @FXML
    public void onAddDevice() {

    }

    @FXML
    public void onRemoveSelected() {
    }

    public void inputChanged(ActionEvent event) {
    }

}