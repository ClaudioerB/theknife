package com.mycompany.theknife;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerChangeDataUser {
   
   @FXML 
   private javafx.scene.text.Text textTitle;

   @FXML 
   private javafx.scene.text.Text textInserire;

   @FXML 
   private javafx.scene.control.TextField textField;

   @FXML 
   private javafx.scene.control.Button saveButton;

   private Gestore gestore;
   private Utente utenteLoggato;
   private Stage myStage;

   @FXML 
   private void initialize() {
      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();
      textField.setText("");
   }

   public void setMyStage(Stage stage) {
      this.myStage = stage;
   }  

   public void setValue(String field) {
      textTitle.setText("Cambia " + field);
      textInserire.setText("Inserire " + field.toLowerCase());
      //textField.setText(field);

      myStage.setTitle(textTitle.getText());
   }


   @FXML 
   private void saveData() throws java.io.IOException {
      
      String newData = textField.getText();
      String fieldToChange = textTitle.getText().toLowerCase().trim();
      
      switch (fieldToChange) {
         case "cambia nome":
            utenteLoggato.setNome(newData);
            break;
         case "cambia cognome":
            utenteLoggato.setCognome(newData);
            break;
         case "cambia indirizzo":
            utenteLoggato.setIndirizzo(newData);
            break;
         case "cambia citta":
            utenteLoggato.setCittà(newData);
            break;
         case "cambia stato":
            utenteLoggato.setStato(newData);
            break;
         case "cambia username":
            utenteLoggato.setUsername(newData);
            break;
         /*case "cambia password":
            utenteLoggato.setPasswordHash(newData);
            break;*/
         default:
            System.out.println("Errore: campo non riconosciuto.");
            break;
      }
      
      myStage.close();
   }

   @FXML 
   private void indietro() throws java.io.IOException {
      myStage.close();
   }
}
