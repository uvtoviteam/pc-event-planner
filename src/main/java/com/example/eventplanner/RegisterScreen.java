package com.example.eventplanner;

import genericclasses.DatabaseComm;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterScreen {

    @FXML
    TextField UserField,PassField,ConfirmPassField,EmailField;

    @FXML
    Button RegisterButton;

    @FXML
    Label TestLabel;

    @FXML
    protected void onCancelButtonClick(){

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
//            stage.setMinWidth(304);
//            stage.setMinHeight(262);
            stage.setTitle("Login");
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
