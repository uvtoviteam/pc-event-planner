package com.example.eventplanner;

import genericclasses.DatabaseComm;
import genericclasses.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RegisterScreen {

    @FXML
    TextField UserField,PassField,ConfirmPassField,EmailField;

    @FXML
    Button RegisterButton,CancelButton;

    @FXML
    Label TestLabel;

    public void setButtonClass(){
        Button buttonsarr[]={CancelButton,RegisterButton};
        List<Button> buttons;
        buttons= Arrays.asList(buttonsarr);
        Session.ButtonConfig(buttons);
    }

    @FXML
    protected void onCancelButtonClick(){
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            Stage stage= new Stage();
            scene = new Scene(fxmlLoader.load());
            LoginController controller=  fxmlLoader.getController();
            controller.setButtonClass();
//            String css = this.getClass().getResource("Style.css").toExternalForm();
//            scene.getStylesheets().add(css);
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Login Screen");
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.show();
            Stage stagelogin= (Stage) RegisterButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onRegisterButtonMenuClick(){
        String user,email,pass;
        pass=null;
        if(PassField.getText().equals(ConfirmPassField.getText()))
            pass= PassField.getText();
        else
            TestLabel.setText("Parolele nu se potrivesc.");
        user=UserField.getText();
        email=EmailField.getText();
        int code=DatabaseComm.register_user(user,pass,email);
        if(code==0)
        {
            System.out.println("Registered successfully");
        }
        else{
            System.out.println("Failed, code:"+code);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            Stage stage= new Stage();
            scene = new Scene(fxmlLoader.load());
            LoginController controller=  fxmlLoader.getController();
            controller.setButtonClass();
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setMinWidth(304);
            stage.setMinHeight(262);
            stage.setTitle("Login Screen");
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.show();
            Stage stagelogin= (Stage) RegisterButton.getScene().getWindow();
            stagelogin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
