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
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable{
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
    private Spinner<Integer> startH = new Spinner<>();

    @FXML
    private Spinner<Integer> startM = new Spinner<>();

    @FXML
    private Spinner<Integer> endH = new Spinner<>();;

    @FXML
    private Spinner<Integer> endM = new Spinner<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //configure spinners
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryHs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryHe = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMe = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        startH.setValueFactory(valueFactoryHs);
        endH.setValueFactory(valueFactoryHe);
        startM.setValueFactory(valueFactoryMs);
        endM.setValueFactory(valueFactoryMe);

        startH.getValueFactory().setValue(12);
        endH.getValueFactory().setValue(12);
        startM.getValueFactory().setValue(0);
        endM.getValueFactory().setValue(0);
    }

    public String twoDigits(int x) {
        if(x < 10)
            return "0" + x;
        else
            return "" + x;
    }

    @FXML
    protected void onCreateButtonClick() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        int numberp = 0;
        LocalDate sd = startDate.getValue(), ed = endDate.getValue();
        LocalDateTime sDate, eDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(!participantNumber.getText().isEmpty())
            numberp = Integer.parseInt(participantNumber.getText());

        int allOK = 1;
        if(name.isEmpty() || description.isEmpty() || participantNumber.getText().isEmpty() || sd == null || ed == null) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please fill all fields!");
            allOK = 0;
        } else {
            if(sd.isAfter(ed)) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Please enter a valid time period!");
                allOK = 0;
            }
        }

        // get the creator id
        User creator = Session.getInstance().getUser();
        int creatorId = DatabaseComm.getUserId(creator);

        if(allOK == 1) {
            sDate = LocalDateTime.parse(sd + " " + twoDigits(startH.getValue()) + ":" + twoDigits(startM.getValue()) + ":00", formatter);
            eDate = LocalDateTime.parse(ed + " " + twoDigits(endH.getValue()) + ":" + twoDigits(endM.getValue()) + ":00", formatter);

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
