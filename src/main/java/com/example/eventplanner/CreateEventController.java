package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
import genericclasses.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    private CheckBox daytimeCheck, nighttimeCheck, weekendCheck, formalCheck, casualCheck, sportsCheck, charityCheck;

    public void setButtonClass(){
        Button buttonsarr[]={cancelButton, createButton};
        List<Button> buttons;
        buttons= Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }


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

        // get the creator id
        User creator = Session.getInstance().getUser();
        int creatorId = DatabaseComm.getUserId(creator);

        if(allOK == 1) {
            Event newEvent = new Event(name, description, sDate, eDate, numberp);
            int code = DatabaseComm.add_event(newEvent, creatorId);

            if (code == 0) {
                Event event = DatabaseComm.getLatestEvent();
              //  System.out.println("latest: " + event);

                // add the filters
                if(daytimeCheck.isSelected())
                    DatabaseComm.add_filter(event, 1);
                if(nighttimeCheck.isSelected())
                    DatabaseComm.add_filter(event, 2);
                if(weekendCheck.isSelected())
                    DatabaseComm.add_filter(event, 3);
                if(formalCheck.isSelected())
                    DatabaseComm.add_filter(event, 4);
                if(casualCheck.isSelected())
                    DatabaseComm.add_filter(event, 5);
                if(sportsCheck.isSelected())
                    DatabaseComm.add_filter(event, 6);
                if(charityCheck.isSelected())
                    DatabaseComm.add_filter(event, 7);

                FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("main-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                    MainMenu controller=  fxmlLoader.getController();
                    controller.setButtonClass();
                    String css = this.getClass().getResource("Style.css").toExternalForm();
                    scene.getStylesheets().add(css);
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
            MainMenu controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
