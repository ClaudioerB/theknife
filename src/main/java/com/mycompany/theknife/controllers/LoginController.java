package com.mycompany.theknife.controllers;

import java.io.IOException;

import com.mycompany.theknife.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {

    @FXML
    private ImageView knifeImageView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private void initialize() {
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
        }
    }
    @FXML
    private Button accediButton;
    
    @FXML
    private void switchToHomeNotLogged() throws IOException {
        App.setRoot("HomeNotLogged");
    }
}