package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Session;
import genericclasses.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginController {
    @FXML
    private TextField UserField;
    @FXML
    private PasswordField PassField;

    @FXML
    private Button RegisterButton,LoginButton;

    public void setButtonClass(){
        Button buttonsarr[]={RegisterButton,LoginButton};
        List<Button> buttons;
        buttons= Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }


    @FXML
    protected void onLoginButtonClick() {
        String user=UserField.getText();
        String pass=PassField.getText();
        int code=DatabaseComm.login_user(user,pass);
        if(code==0)
        {
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
                Stage stagelogin= (Stage) LoginButton.getScene().getWindow();
                stagelogin.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //move to main menu

            User sessionUser = DatabaseComm.getUserInfo(user);
            Session session = Session.getInstance();
            session.setUser(sessionUser);
        }
        else{
            //Error message for wrong name or password
            System.out.println("Wrong user/pass");
        }
        //System.out.println("User is "+user);
        //System.out.println("Pass is "+pass);
    }
    @FXML
    protected void onRegisterButtonClick() {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterScreen.class.getResource("register-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            RegisterScreen controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage= new Stage();
            stage.setMinWidth(209);
            stage.setMinHeight(400);
            stage.setTitle("Register Screen");
            stage.setScene(scene);
            stage.show();
            Stage stagelogin= (Stage) RegisterButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}