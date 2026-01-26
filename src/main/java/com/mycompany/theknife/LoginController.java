package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * @author TheKnifeTeam
 * 
 * LoginController rappresenta il controller per la scena di Login.<br>
 * Si occupa di gestire l'interfaccia di login del'utente e la sua registrazione.<br>
 * 
 * @version 1.0
 */
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

    private GestoreUtenti gestoreUtenti;

    /**
     * Metodo FXML che inizializza la scena di login.<br>
     * Inizializza l'immagine del logo.<br>
     */
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
    
    /**
     * Metodo FXML che inizializza la scena di registrazione.<br>
     * @throws IOException
     */
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("CreateUser");
    }

    /**
     * Metodo FXML che porta alla schermata HomeLogged dopo aver verificato le credenziali di accesso.<br>
     * @throws IOException
     */
    @FXML
    private void switchToHomeLogged() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        
        gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        Gestore gestore = Gestore.getGestore();
        
        //caldarolacagilio Mo8!Cq97 -> esempio 
        //System.out.println(gestoreUtenti.verificaCredenziali(username, password));
        if (gestoreUtenti.verificaCredenziali(username, password)) {
            gestore.setUtenteLoggato(gestoreUtenti.getUtenteByUsername(username));
            GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
            gestoreDataset.ordinaDataSet(gestore.getUtenteLoggato());
            gestore.getUtenteLoggato().setPreferiti(gestoreUtenti.getPreferitiUtente(username));
            gestoreUtenti.printPreferitiUtente();
            App.setRoot("HomeLogged");
        } else {
            loginErrorMessageLabel.setVisible(true);
        }
    }
    
    /**
     * Metodo FXML che porta alla schermata HomeNotLogged.<br>
     * @throws IOException
     */
    @FXML
    private void switchToHomeNotLogged() throws IOException {
        App.setRoot("HomeNotLogged");
    }
}