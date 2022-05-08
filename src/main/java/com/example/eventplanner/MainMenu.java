package com.example.eventplanner;

import genericclasses.Event;
import genericclasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    @FXML
    Button TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,ViewEventButton,RefreshButton,EventManagementButton;

    @FXML
    TextField SearchField;

    @FXML
    Text NotifText;

    @FXML
    TableView<EventModel> eventTableView = new TableView<>();

    @FXML
    public TableColumn<Event, Integer> eventID;
    @FXML
    public TableColumn<Event,String> eventName;

    @FXML
    public TableColumn<Event, Integer> participantsNum;

    @FXML
    public TableColumn<Event, LocalDateTime> startDate;

    @FXML
    public TableColumn<Event, LocalDateTime> endDate;

    List<Event> eventList=new ArrayList<>();
    ArrayList<User> userList=new ArrayList<>();
    private void addUser(){
        userList.add(new User(2, "Ion", "12345678", "ion.gmail.com"));
    }


    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            new EventModel( 1,"Event 1","Test",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(),50)
            );
    @FXML
    protected void onTestEvent(){
        addUser();
        eventModels.add(new EventModel( 2,"Event 2","Test event description",LocalDateTime.now(),LocalDateTime.now(),userList,50));
      eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
      eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
      participantsNum.setCellValueFactory(new PropertyValueFactory<>("Limit"));
      startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
      endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
      eventTableView.setItems(eventModels);
    }

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
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("event-manager-view.fxml"));
        Scene scene = null;
        try {
            Stage stage= new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Event Management");
            stage.setScene(scene);
            //stage.setResizable(false);
            EventManagerController controller=  fxmlLoader.getController();
            controller.start(new User(1,"ion","test","ret@gmail.com"),stage); // will need an actual user with id
            stage.show();
            Stage stagelogin= (Stage) EventManagementButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onSettingsButtonClick(){

    }
    @FXML
    protected void onCalendarButtonClick(){

    }

    @FXML
    protected void onMouseClickTable(ActionEvent event){

    }

}
