package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateEventController {
    @FXML
    private TextField nameField, participantNumber;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private Button cancelButton, createButton;

//    private ObservableList<EventModel> eventModel;
//
//    @FXML
//    public TableColumn<Event, Integer> eventID;
//    @FXML
//    public TableColumn<Event,String> eventName;
//
//    @FXML
//    public TableColumn<Event, Integer> participantsNum;
//
//    @FXML
//    public TableColumn<Event, LocalDateTime> startDateT;
//
//    @FXML
//    public TableColumn<Event, LocalDateTime> endDateT;
//
//    @FXML
//    TableView<EventModel> eventTableView = MainMenu.getEventTableView();



    @FXML
    protected void onCreateButtonClick() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        int numberp = Integer.parseInt(participantNumber.getText());

        LocalDateTime sDate = startDate.getValue().atStartOfDay();
        LocalDateTime eDate = endDate.getValue().atStartOfDay();

        Event newEvent = new Event(name, description, sDate, eDate, numberp);
        int code= DatabaseComm.add_event(newEvent);

        if(code==0)
        {
//            Event event = DatabaseComm.getEventDetails(newEvent.getNume());
//            System.out.println(event);
//            eventModel = FXCollections.observableArrayList(
//                    new EventModel(event.getId(), event.getNume(), event.getDescription(), event.getStartdateLocal(), event.getEnddateLocal(), new ArrayList<User>(),event.getLimit())
//            );
//
//            eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
//            eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
//            participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
//            startDateT.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
//            endDateT.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
//            eventTableView.setItems(eventModel);

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
                Stage stagelogin= (Stage) createButton.getScene().getWindow();
                stagelogin.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //move to main menu
        }
        else{
            //Error message
            System.out.println("Couldn't create event");
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
            Stage stagelogin= (Stage) createButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //move to main menu
    }

}
