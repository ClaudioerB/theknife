package com.mycompany.theknife;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerChangePasswordUser rappresenta il controller per la scena ChangePasswordUser.<br>
 * Si occupa di gestire l'interfaccia di modifica della password dell'utente.<br>
 * 
 * @version 1.0
 */
public class ControllerChangePasswordUser {

   @FXML 
   private javafx.scene.control.PasswordField pswField;

   @FXML 
   private javafx.scene.control.PasswordField pswFieldNew;

   @FXML 
   private javafx.scene.control.Button saveButton;

   @FXML 
   private javafx.scene.control.Label labelError;

   @FXML 
   private javafx.scene.control.Label labelError1;

   private Gestore gestore;
   private Utente utenteLoggato;
   private Stage myStage;
   private GestoreUtenti gestoreUtenti;

   /**
    * Metodo FXML che inizializza il controller e imposta i valori iniziali.<br>
    */
   @FXML 
   private void initialize() {
      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();
      labelError.setVisible(false);
      labelError1.setVisible(false);
      pswField.setText("");
      pswFieldNew.setText("");
   }

   /**
    * Metodo che imposta la stage.<br>
    * @param stage stage da impostare
    */
   public void setMyStage(Stage stage) {
      this.myStage = stage;
   }  

   /**
    * Metodo FXML che salva la password dell'utente.<br>
    * Chiama il metodo controlloPassword per verificare la correttezza della password.<br>
    * Chiama il metodo close per chiudere la stage.<br>
    * @throws java.io.IOException
    */
   @FXML 
   private void saveData() throws java.io.IOException {
      
      String data = pswField.getText();
      String dataNew = pswFieldNew.getText();
      gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      labelError.setVisible(false);
      labelError1.setVisible(false);

      if (data.isEmpty() || !data.equals(utenteLoggato.getPasswordHash())) {
         labelError1.setVisible(true);
         if (dataNew.isEmpty()) {
            labelError.setVisible(true);
         }
         return;
      }
      else {
         if (!dataNew.isEmpty() && !dataNew.equals(data) && gestoreUtenti.controlloPassword(dataNew)) {
            utenteLoggato.setPasswordHash(dataNew);
            myStage.close();
         } else {
            labelError.setVisible(true);
         }
      }

   }

   /**
    * Metodo FXML che chiude la stage prima di salvare la password.<br>
    * @throws java.io.IOException
    */
   @FXML 
   private void indietro() throws java.io.IOException {
      myStage.close();
   }
}
