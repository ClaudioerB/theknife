package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ControllerCreateUser {

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
    private javafx.scene.control.Label creationUsernameErrorMessageLabel;

    @FXML 
    private javafx.scene.control.Label creationEmailErrorMessageLabel;
    @FXML 
    private javafx.scene.control.Label creationEmailError2MessageLabel;
    @FXML 
    private javafx.scene.control.Label creationPassword1ErrorMessageLabel;
    @FXML 
    private javafx.scene.control.Label creationPassword2ErrorMessageLabel;
    


    @FXML
    private void initialize() {
        creationUsernameErrorMessageLabel.setVisible(false);
        creationEmailErrorMessageLabel.setVisible(false);
        creationPassword1ErrorMessageLabel.setVisible(false);
        creationPassword2ErrorMessageLabel.setVisible(false);
        creationEmailError2MessageLabel.setVisible(false);
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
        GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        Utente utente= new Utente(username, password, email, nome, cognome, stato, citta, indirizzo, isRistoratore);
        if (gestoreUtenti.creaUtente(utente)==0) {
            
            App.setRoot("HomeLogged");
        } else {
            if(gestoreUtenti.creaUtente(utente)==2)
                creationEmailErrorMessageLabel.setVisible(true);
            if(gestoreUtenti.creaUtente(utente)==1)
                creationUsernameErrorMessageLabel.setVisible(true);
            if(gestoreUtenti.creaUtente(utente)==3){
                creationPassword1ErrorMessageLabel.setVisible(true);
                creationPassword2ErrorMessageLabel.setVisible(true);
            }
            if(gestoreUtenti.creaUtente(utente)==4){
                creationEmailError2MessageLabel.setVisible(true);
            }

        }
    }
    
    @FXML
    private void CheckBoxes1Checker() {
        if (clienteCheckBox.isSelected()) {
            ristoratoreCheckBox.setSelected(false);
        }
        
    }

    @FXML
    private void CheckBoxes2Checker() {
        
        if (ristoratoreCheckBox.isSelected()) {
            clienteCheckBox.setSelected(false);
        }
    }
    
    @FXML
    private void switchToHomeNotLogged() throws IOException {
        App.setRoot("HomeNotLogged");
    }
}