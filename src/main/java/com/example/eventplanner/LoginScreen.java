package com.example.eventplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller=  fxmlLoader.getController();
        controller.setButtonClass();
       // String css = this.getClass().getResource("Style.css").toExternalForm();
        //scene.getStylesheets().add(css);
        stage.setMinWidth(304);
        stage.setMinHeight(262);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();
        // run thread
    }

    public static void main(String[] args) {
        launch();
    }
}