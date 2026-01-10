package com.mycompany.theknife;

import java.io.IOException;

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
    private javafx.scene.control.Label loginErrorMessageLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button accediButton;

    @FXML
    private void initialize() {
        loginErrorMessageLabel.setVisible(false);
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
    private void switchToRegister() throws IOException {
       
        App.setRoot("CreateUser");
    }

    @FXML
    private void switchToHomeLogged() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        Gestore gestore = Gestore.getGestore();
        if (gestoreUtenti.verificaCredenziali(username, password)) {
            gestore.setUtenteLoggato(gestoreUtenti.getUtenteByUsername(username));
            App.setRoot("HomeLogged");
        } else {
            loginErrorMessageLabel.setVisible(true);
        }
    }
    
    @FXML
    private void switchToHomeNotLogged() throws IOException {
        App.setRoot("HomeNotLogged");
    }
}