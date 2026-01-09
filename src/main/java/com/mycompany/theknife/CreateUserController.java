package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CreateUserController {

    @FXML
    private ImageView knifeImageView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField cognomeTextField;

    @FXML
    private Button createUserButton;

    @FXML
    private TextField statoTextField;
    
    @FXML
    private TextField cittaTextField;

    @FXML
    private TextField indirizzoTextField;

    @FXML
    private CheckBox clienteCheckBox;

    @FXML
    private CheckBox ristoratoreCheckBox;

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
    private void createUser() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        String stato = statoTextField.getText();
        String citta = cittaTextField.getText();
        String indirizzo = indirizzoTextField.getText();
        boolean isCliente = clienteCheckBox.isSelected();
        boolean isRistoratore = ristoratoreCheckBox.isSelected();
        GestoreUtenti gestoreUtenti = new GestoreUtenti();
        if (gestoreUtenti.creaUtente(username, password, email, nome, cognome, stato, citta, indirizzo, isRistoratore)) {
            App.setRoot("Login");
        } else {
            loginErrorMessageLabel.setVisible(true);
        }
    }
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("Register");
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