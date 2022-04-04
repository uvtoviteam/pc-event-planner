package com.example.eventplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    @FXML
    Button LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,ViewEventButton,RefreshButton,EventManagementButton;

    @FXML
    TextField SearchField;

    @FXML
    Text NotifText;

    @FXML
    ImageView logo;

    @FXML
    protected void onCreateEventButtonClick(){

    }
    @FXML
    protected void onNotifButtonClick(){

    }
    @FXML
    protected void onRefreshButtonClick(){

    }
    @FXML
    protected void onViewEventButtonClick(){

    }
    @FXML
    protected void onLogoutButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage= new Stage();
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Login Screen");
            stage.setScene(scene);
            stage.show();
            Stage stagelogin= (Stage) LogoutButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onEventManagementButtonClick(){

    }
    @FXML
    protected void onSettingsButtonClick(){

    }
    @FXML
    protected void onCalendarButtonClick(){

    }
}
