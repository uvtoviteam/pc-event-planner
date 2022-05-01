package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateEventController {
    @FXML
    private TextField nameField, participantNumber;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private Button cancelButton, createButton;

    @FXML
    private Label errorLabel;

    @FXML
    protected void onCreateButtonClick() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        int numberp = 0;
        LocalDate sd = startDate.getValue(), ed = endDate.getValue();
        LocalDateTime sDate = LocalDateTime.now(), eDate = LocalDateTime.now();

        if(!participantNumber.getText().isEmpty())
            numberp = Integer.parseInt(participantNumber.getText());

        if(sd != null)
            sDate = sd.atStartOfDay();
        if(ed != null)
            eDate = ed.atStartOfDay();

        int allOK = 1;
        if(name.isEmpty() || description.isEmpty() || participantNumber.getText().isEmpty() || sd == null || ed == null) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please fill all fields!");
            allOK = 0;
        } else {
            if(sDate.isAfter(eDate)) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Please enter a valid time period!");
                allOK = 0;
            }
        }

        if(allOK == 1) {
            Event newEvent = new Event(name, description, sDate, eDate, numberp);
            int code = DatabaseComm.add_event(newEvent);

            if (code == 0) {
                Event event = DatabaseComm.getEventDetails(newEvent.getNume());
                System.out.println("latest: " + event);

                FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("main-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setMinWidth(640);
                    stage.setMinHeight(480);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                    Stage stageN = (Stage) createButton.getScene().getWindow();
                    stageN.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //move to main menu
            } else {
                //Error message
                System.out.println("Couldn't create event");
            }
        }
    }

    @FXML
    protected void onCancelButtonClick() {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage= new Stage();
            stage.setMinWidth(640);
            stage.setMinHeight(480);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
            Stage stageN = (Stage) cancelButton.getScene().getWindow();
            stageN.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //move to main menu
    }

}
