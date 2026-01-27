package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * @author TheKnifeTeam
 * 
 * LoginController rappresenta il controller per la scena di CreateUser.<br>
 * Si occupa di gestire l'interfaccia di creazione di un nuovo utente.<br>
 * 
 * @version 1.0
 */
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
    private javafx.scene.control.Label creationStatoErrorMessageLabel;
    @FXML 
    private javafx.scene.control.Label creationEmailErrorMessageLabel;
    @FXML 
    private javafx.scene.control.Label creationEmailError2MessageLabel;
    @FXML 
    private javafx.scene.control.Label creationPassword1ErrorMessageLabel;
    @FXML 
    private javafx.scene.control.Label creationPassword2ErrorMessageLabel;

    private GestoreDataset gestoreDataset;
    private Gestore gestore;
    /**
     * Metodo FXML che inizializza la scena di registrazione.<br>
     * Inizializza l'immagine del logo e i vari textField.<br>
     */
    @FXML
    private void initialize() {
        creationUsernameErrorMessageLabel.setVisible(false);
        creationEmailErrorMessageLabel.setVisible(false);
        creationPassword1ErrorMessageLabel.setVisible(false);
        creationPassword2ErrorMessageLabel.setVisible(false);
        creationEmailError2MessageLabel.setVisible(false);
        creationStatoErrorMessageLabel.setVisible(false);
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
        } else{
            knifePath=System.getProperty("user.dir")+ "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
            knifeFile = new java.io.File(knifePath);
            if (knifeFile.exists()) {       
                knifeImageView.setImage(
                        new javafx.scene.image.Image(knifeFile.toURI().toString())
                );
                
                knifeImageView.setVisible(true); 
            }
        }
        gestore = Gestore.getGestore();
        gestoreDataset = GestoreDataset.getGestoreDataset();
    }
    /**
     * Metodo FXML che crea un nuovo utente.<br>
     * Crea il nuovo utente controllando che abbia inserito tutti i dati richiesti.<br>
     * Chiama il metodo creaUtente del GestoreUtenti per aggiungere l'utente al dataset.<br>
     * @throws IOException
     */
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
        GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
        
        int validazione = gestoreDataset.validaStatoCitta(citta, stato);
        if (validazione == 1) {
            creationStatoErrorMessageLabel.setVisible(true);
            return;
        } else if (validazione == 2) {
            creationStatoErrorMessageLabel.setText("La città non può essere vuota");
            creationStatoErrorMessageLabel.setVisible(true);
            return;
        }
        
        creationStatoErrorMessageLabel.setVisible(false);
        creationEmailErrorMessageLabel.setVisible(false);
        creationUsernameErrorMessageLabel.setVisible(false);
        creationPassword1ErrorMessageLabel.setVisible(false);
        creationPassword2ErrorMessageLabel.setVisible(false);
        creationEmailError2MessageLabel.setVisible(false);
        
        Utente utente = new Utente(username, password, email, nome, cognome, stato, citta, indirizzo, isRistoratore);
        int risultato = gestoreUtenti.creaUtente(utente);
        
        if (risultato == 0) {
            verificaECreaStatoCitta(citta, stato);
            gestore.setUtenteLoggato(gestoreUtenti.getUtenteByUsername(username));
            gestoreDataset.ordinaDataSet(gestore.getUtenteLoggato());
            gestore.getUtenteLoggato().setPreferiti(gestoreUtenti.getPreferitiUtente(username));
            gestoreUtenti.printPreferitiUtente();
            App.setRoot("HomeLogged");
        } else {
            if (risultato == 2)
                creationEmailErrorMessageLabel.setVisible(true);
            if (risultato == 1)
                creationUsernameErrorMessageLabel.setVisible(true);
            if (risultato == 3) {
                creationPassword1ErrorMessageLabel.setVisible(true);
                creationPassword2ErrorMessageLabel.setVisible(true);
            }
            if (risultato == 4) {
                creationEmailError2MessageLabel.setVisible(true);
            }
        }
    }
    /**
    * Metodo che verifica e crea il stato e la citta.<br>
    * @param citta Città da verificare
    * @param stato Stato da verificare
    */
    private boolean verificaECreaStatoCitta(String citta, String stato) {
        if (citta.isEmpty()) {
            System.out.println("Città vuota: skip creazione");
            return false;
        }
        
        int validazione = gestoreDataset.validaStatoCitta(citta, stato);
        
        if (validazione == 0) {
            String statoTrovato = gestoreDataset.findStatoByCitta(citta);
            if (statoTrovato == null || statoTrovato.isEmpty()) {
                gestoreDataset.addNewStato(stato);
                gestoreDataset.addNewCitta(stato, citta);
            }
            return true;
        } else if (validazione == 1) {
            String statoEsistente = gestoreDataset.findStatoByCitta(citta);
            System.out.println("ERRORE: " + citta + " esiste già in " + statoEsistente);
            return false;
        }
        
        return false;
    }
    
    /**
     * Metodo FXML che controlla se il checkbox del cliente è selezionato.<br>
     * Se il checkbox del cliente è selezionato, il checkbox del ristoratore viene deselezionato.<br>
     * Utilizzato per indicare se l'utente si vuole registrare come cliente.<br>
     */
    @FXML
    private void CheckBoxes1Checker() {
        if (clienteCheckBox.isSelected()) {
            ristoratoreCheckBox.setSelected(false);
        }
    }

    /**
     * Metodo FXML che controlla se il checkbox del ristoratore è selezionato.<br>
     * Se il checkbox del ristoratore è selezionato, il checkbox del cliente viene deselezionato.<br>
     * Utilizzato per indicare se l'utente si vuole registrare come ristoratore.<br>
     */
    @FXML
    private void CheckBoxes2Checker() {
        if (ristoratoreCheckBox.isSelected()) {
            clienteCheckBox.setSelected(false);
        }
    }
    
    /**
     * Metodo FXML che porta alla schermata HomeNotLogged cioè senza l'accesso.<br>
     * @throws IOException
     */
    @FXML
    private void switchToHomeNotLogged() throws IOException {
        App.setRoot("HomeNotLogged");
    }
}