package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
import genericclasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventManagerController {
    @FXML
    private Button BackButton;

    @FXML
    private Button CalendarButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button NotifButton;

    @FXML
    private Text NotifText;

    @FXML
    private TextField SearchField;

    @FXML
    private Button SettingsButton;

    @FXML
    private TableColumn<?, ?> endDate;

    @FXML
    private TableColumn<?, ?> eventID;

    @FXML
    private TableColumn<?, ?> eventName;

    @FXML
    private TableView<EventModel> eventManagerTableView=new TableView<>();

    @FXML
    private ImageView logo;

    @FXML
    private TableColumn<?, ?> participantsNum;

    @FXML
    private TableColumn<?, ?> startDate;

    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            //new EventModel( 1,"Event 1","Test",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(),50,1)
    );

    void start(User person,Stage stage){
        int currentId=person.getId();
        eventManagerTableView.setRowFactory(tv -> {
            TableRow<EventModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EventModel rowData = row.getItem();
                    startEditEvent(rowData);
                    System.out.println("Double click on: "+rowData.getNume());
                }
            });
            return row ;
        });
        //eventModels.add(new EventModel( 2,"Event 2","Test event description", LocalDateTime.now(),LocalDateTime.now(),userList,50));
        System.out.println("Current id:"+currentId);
        eventModels=DatabaseComm.refreshlist(currentId,true);
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventManagerTableView.setItems(eventModels);
        //eventManagerTableView.refresh();
        System.out.println("Complete.");

    }

    void startEditEvent(EventModel event){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("event-edit-view.fxml"));
        Scene scene = null;
        try {
            Stage stage = new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            //stage.setResizable(false);
            EventEditController controller=  fxmlLoader.getController();
            controller.setEventData(event);
            stage.show();
            Stage stagelogin = (Stage) BackButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onCalendarButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) {

    }

    @FXML
    void onMouseClickTable(MouseEvent event) {

    }

    @FXML
    void onNotifButtonClick(ActionEvent event) {

    }

    @FXML
    void onSettingsButtonClick(ActionEvent event) {

    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            Stage stage = new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.show();
            Stage stagelogin = (Stage) BackButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
