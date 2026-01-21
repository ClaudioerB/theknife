package com.mycompany.theknife;

import javafx.fxml.FXML;
import javafx.stage.Stage;

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

   @FXML 
   private void initialize() {
      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();
      labelError.setVisible(false);
      labelError1.setVisible(false);
      pswField.setText("");
      pswFieldNew.setText("");
   }

   public void setMyStage(Stage stage) {
      this.myStage = stage;
   }  


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

   @FXML 
   private void indietro() throws java.io.IOException {
      myStage.close();
   }
}
