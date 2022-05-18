package com.uygar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("home"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println(App.class.getResourceAsStream("fxml/home.fxml"));
        InputStream resourceAsStream = App.class.getResourceAsStream("fxml/"+fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add(App.class.getResource("css/main.css").toExternalForm());
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}