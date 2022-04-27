package com.example.eventplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EventEditController {

    @FXML
    private Button BackButton;

    @FXML
    private TextArea DescArea;

    @FXML
    private DatePicker EndDateField;

    @FXML
    private TextField EventNameField;

    @FXML
    private Button SaveButton;

    @FXML
    private DatePicker StartDateField;

    @FXML
    private TableView<?> UserTable;

    @FXML
    void onBackButtonPress(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("event-manager-view.fxml"));
        Scene scene = null;
        try {
            Stage stage= new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Event Manager");
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.show();
            Stage stagelogin= (Stage) BackButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onSaveButtonPress(ActionEvent event) {

    }

}

