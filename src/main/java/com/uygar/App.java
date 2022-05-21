package com.uygar;

import com.github.kevinsawicki.http.HttpRequest;
import com.uygar.properties.ApplicationProperties;
import com.uygar.repo.DeviceRepository;
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
    public static final DeviceRepository deviceRepository = new DeviceRepository();
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
        if (userId <= 0) {
            ParentControllerPair<Parent, Object> login = getParentControllerPair("login", "login.css");
            scene = new Scene(login.getParent(), 450, 400);
        } else {
            ParentControllerPair<Parent, Object> home = getParentControllerPair("home", "main.css");
            scene = new Scene(home.getParent());
        }

        stage.setScene(scene);
        stage.show();
    }

    public static <PARENT extends Parent> PARENT getParent(String fxml, String stylePath) throws IOException {
        ParentControllerPair<PARENT, Object> parentControllerPair = App.getParentControllerPair(fxml, stylePath);
        return parentControllerPair.getParent();
    }

    public static <CONTROLLER> CONTROLLER getController(String fxml, String stylePath) throws IOException {
        ParentControllerPair<Parent, CONTROLLER> parentControllerPair = App.getParentControllerPair(fxml, stylePath);
        return parentControllerPair.getController();
    }

    public static <PARENT extends
            Parent, CONTROLLER> ParentControllerPair<PARENT, CONTROLLER> getParentControllerPair(String fxml, String stylePath) throws
            IOException {
        InputStream resourceAsStream = App.class.getResourceAsStream("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        PARENT parent = fxmlLoader.load(resourceAsStream);
        parent.getStylesheets().add(Objects.requireNonNull(App.class.getResource("css/" + stylePath)).toExternalForm());
        CONTROLLER controller = fxmlLoader.getController();

        return new ParentControllerPair<>(parent, controller);
    }

    public static void main(String[] args) {
        launch();
    }

}