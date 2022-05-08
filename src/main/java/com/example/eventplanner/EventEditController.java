package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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
    private Spinner<Integer> Hour1=new Spinner<>();

    @FXML
    private Spinner<Integer> Hour2=new Spinner<>();

    @FXML
    private Spinner<Integer> Min1=new Spinner<>();;

    @FXML
    private Spinner<Integer> Min2=new Spinner<>();

    EventModel currentEvent;

    public void setButtonClass(){
        Button buttonsarr[]={SaveButton,BackButton};
        List<Button> buttons;
        buttons= Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }


    @FXML
    void onBackButtonPress(ActionEvent event) {
        goBack();
    }

    void goBack(){
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
            EventManagerController controller=  fxmlLoader.getController();
            controller.start(Session.getInstance().getUser(),stage); // will need an actual user with id
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.show();
            stage.show();
            Stage stagelogin= (Stage) BackButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void setEventData(EventModel event){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryMin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        EventNameField.setText(event.getNume());
        DescArea.setText(event.getDescription());
        StartDateField.setValue(event.getDate(true));
        EndDateField.setValue(event.getDate(false));
        Hour1.setValueFactory(valueFactory1);
        Hour2.setValueFactory(valueFactory2);
        Min1.setValueFactory(valueFactoryMin1);
        Min2.setValueFactory(valueFactoryMin2);
        Hour1.getValueFactory().setValue(event.getHour(true));
        Hour2.getValueFactory().setValue(event.getHour(false));
        Min1.getValueFactory().setValue(event.getMinute(true));
        Min2.getValueFactory().setValue(event.getMinute(false));
        currentEvent=event;
    }

    @FXML
    void onSaveButtonPress(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sDate,eDate;
        String eName,Desc;
        eName = EventNameField.getText();
        Desc = DescArea.getText();
        String formattedHour1 = String.format("%02d", Hour1.getValue());
        String formattedHour2 = String.format("%02d", Hour2.getValue());
        String formattedMin1 = String.format("%02d", Min1.getValue());
        String formattedMin2 = String.format("%02d", Min2.getValue());
        sDate = LocalDateTime.parse(StartDateField.getValue()+" "+formattedHour1+":"+formattedMin1+":00",formatter);
        eDate = LocalDateTime.parse(EndDateField.getValue()+" "+formattedHour2+":"+formattedMin2+":00",formatter);
        System.out.println(sDate);
        System.out.println(eDate);
        if(DatabaseComm.updateEvent(currentEvent)==0){
            //DatabaseComm.commitQueries();
            currentEvent.setNume(eName);
            currentEvent.setDescription(Desc);
            currentEvent.setStartDatePrivate(sDate);
            currentEvent.setEndDatePrivate(eDate);
            DatabaseComm.updateEvent(currentEvent);
            goBack();}
        else{
            System.out.println("Error occured.");
        }
    }

}

