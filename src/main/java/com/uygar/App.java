package com.uygar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("home", "main.css"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml, String stylePath) throws IOException {
        InputStream resourceAsStream = App.class.getResourceAsStream("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add(Objects.requireNonNull(
                App.class.getResource("css/" + stylePath)).toExternalForm());
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}