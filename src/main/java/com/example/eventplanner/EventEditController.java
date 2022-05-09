package com.example.eventplanner;

import genericclasses.Event;
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
    }

    @FXML
    void onSaveButtonPress(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sDate,eDate;
        String eName,Desc;
        eName = EventNameField.getText();
        Desc = EventNameField.getText();
        sDate = LocalDateTime.parse(StartDateField.getValue()+" "+Hour1.getValue()+":"+Min1.getValue()+":00",formatter);
        eDate = LocalDateTime.parse(EndDateField.getValue()+" "+Hour2.getValue()+":"+Min2.getValue()+":00",formatter);
        System.out.println(sDate);
        System.out.println(eDate);
        goBack();
    }

}

