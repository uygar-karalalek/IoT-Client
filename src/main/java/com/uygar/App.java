package com.uygar;

import com.github.kevinsawicki.http.HttpRequest;
import com.uygar.properties.ApplicationProperties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class App extends Application {

    public static final ApplicationProperties applicationProperties = new ApplicationProperties();
    public static int userId = -1;

    @Override
    public void start(Stage stage) throws IOException {
        String username = applicationProperties.getProperty("username");
        String password = applicationProperties.getProperty("password");

        try {
            String spec = "http://127.0.0.1:8000/userId?username=" + username + "&password=" + password;
            URL url = new URL(spec);
            userId = Integer.parseInt(HttpRequest.get(url).accept("text/plain").body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene;
        if (userId <= 0) scene = new Scene(loadFXML("login", "login.css"), 450, 400);
        else scene = new Scene(loadFXML("home", "main.css"));

        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml, String stylePath) throws IOException {
        InputStream resourceAsStream = App.class.getResourceAsStream("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add(Objects.requireNonNull(App.class.getResource("css/" + stylePath)).toExternalForm());
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}