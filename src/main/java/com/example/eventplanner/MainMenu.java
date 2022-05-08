package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Event;
import genericclasses.Session;
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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu {
    @FXML
    Button TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton;

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

    public void setButtonClass(){
        Button buttonsarr[]={TestEventButton, LogoutButton,CalendarButton,NotifButton,SettingsButton,CreateEventButton,RefreshButton,EventManagementButton};
        List<Button> buttons;
        buttons=Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }

    private void addUser(){
        userList.add(new User(2, "Ion", "ion.gmail.com"));
    }


    private ObservableList<EventModel> eventModels = FXCollections.observableArrayList(
            new EventModel( 1," SLjazzing","Este vorba despre o plimbare muzicala cu Tramvaiul Turistic ce strabate orasul de pe Bega, totul pe acorduri Jazzy, așa cum v-am obișnuit.",LocalDateTime.now(),LocalDateTime.now(),new ArrayList<User>(), 50,1)
            );
    @FXML
    protected void onTestEvent(){
        addUser();
        eventModels.add(new EventModel( 2,"Street Food Festival","Dacă îți dorești un prânz/ o cină în aer liber, sau doar vrei să ieși la o băutură rece alături de prieteni, la Iulius Town vei putea face asta, iar noi te așteptăm cu brațele deschise!",LocalDateTime.now(),LocalDateTime.now(),userList,50,1));
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
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("create-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            CreateEventController controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage= new Stage();
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Create Event");
            stage.setScene(scene);
            stage.show();
            Stage stagelogin= (Stage) CreateEventButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onNotifButtonClick(){

    }
    @FXML
    protected void onRefreshButtonClick(){
        int currentUser = Session.getInstance().getUser().getId();
        eventTableView.getItems().clear();
        eventModels = DatabaseComm.refreshlist(currentUser,false);
        eventID.setCellValueFactory(new PropertyValueFactory<>("ID"));  //GETTER NAME
        eventName.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        participantsNum.setCellValueFactory(new PropertyValueFactory<>("Usersize"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Startdate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("Enddate"));
        eventTableView.setItems(eventModels);
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
            LoginController controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
            controller.start(Session.getInstance().getUser(),stage); // will need an actual user with id
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
    protected void onMouseClickTable(){
        //eventTableView.getSelectionModel().getSelectedItem();
        EventModel eventSelected = eventTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("View_Event.fxml"));
        Scene scene = null;
        if (eventSelected == null) System.out.println("couldn't find event");
        else {


            try {
                Stage stage = new Stage();
                scene = new Scene(fxmlLoader.load());
                stage.setMinWidth(304);
                stage.setMinHeight(262);
                stage.setTitle("View Event");
                stage.setScene(scene);
                //stage.setResizable(false);
                ViewEventController controller = fxmlLoader.<ViewEventController>getController();
                controller.setButtonClass();
                String css = this.getClass().getResource("Style.css").toExternalForm();
                scene.getStylesheets().add(css);
                controller.initialize(eventSelected);
                stage.show();


            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }

}
