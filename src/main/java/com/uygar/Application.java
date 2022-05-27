package com.uygar;

import com.github.kevinsawicki.http.HttpRequest;
import com.uygar.controller.Controller;
import com.uygar.controller.HomeController;
import com.uygar.controller.LoginController;
import com.uygar.properties.ApplicationProperties;
import com.uygar.repo.mysql.DeviceMySqlRepository;
import com.uygar.repo.mysql.DeviceSensorMySqlRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private final ApplicationProperties appProps = new ApplicationProperties();
    private final DeviceMySqlRepository deviceRepository = new DeviceMySqlRepository();
    private final DeviceSensorMySqlRepository deviceSensorMySqlRepository = new DeviceSensorMySqlRepository();
    private int userId = -1;

    @Override
    public void start(Stage stage) throws IOException {
        updateUserId();
        Scene scene;

        if (userId <= 0) {
            ParentControllerPair<Parent, LoginController> login = getParentControllerPair("login", "login.css");
            login.getController().setParentApplication(this);
            login.getController().setLoginStage(stage);
            scene = new Scene(login.getParent(), 450, 400);
        } else {
            ParentControllerPair<Parent, HomeController> home = getParentControllerPair("home", "main.css");
            home.getController().setHomeStage(stage);
            home.getController().setParentApplication(this);
            home.getController().init();
            scene = new Scene(home.getParent());
        }

        stage.setScene(scene);
        stage.show();
    }

    public void updateUserId() {
        String username = appProps.getProperty("username");
        String password = appProps.getProperty("password");

        try {
            String spec = appProps.getClientEndPoint() + "/userId?username=" + username + "&password=" + password;
            URL url = new URL(spec);
            userId = Integer.parseInt(HttpRequest.get(url).accept("text/plain").body());
        } catch (IOException e) {
            e.printStackTrace();
            userId = -1;
        }
    }

    public static <PARENT extends Parent, CONTROLLER extends Controller> ParentControllerPair<PARENT, CONTROLLER>
    getParentControllerPair(String fxml, String stylePath) throws IOException {
        InputStream resourceAsStream = Application.class.getResourceAsStream("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        PARENT parent = fxmlLoader.load(resourceAsStream);
        parent.getStylesheets().add(Objects.requireNonNull(Application.class.getResource("css/" + stylePath)).toExternalForm());
        CONTROLLER controller = fxmlLoader.getController();

        return new ParentControllerPair<>(parent, controller);
    }

    public static void main(String[] args) {
        launch();
    }

    public ApplicationProperties getAppProps() {
        return appProps;
    }

    public DeviceMySqlRepository getDeviceRepository() {
        return deviceRepository;
    }

    public DeviceSensorMySqlRepository getDeviceSensorRepository() {
        return deviceSensorMySqlRepository;
    }

    public int getUserId() {
        return userId;
    }
}