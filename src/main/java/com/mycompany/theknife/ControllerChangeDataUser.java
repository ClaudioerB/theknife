package com.mycompany.theknife;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerChangeDataUser rappresenta il controller per la scena ChangeDataUser.<br>
 * Si occupa di gestire l'interfaccia di modifica dei vari dati dell'utente.<br>
 * 
 * @version 1.0
 */
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

   /**
    * Metodo FXML che inizializza il controller e imposta i valori iniziali.<br>
    * 
    */
   @FXML 
   private void initialize() {
      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();
      textField.setText("");
   }

   /**
    * Metodo che imposta la stage.<br>
    * @param stage stage da impostare
    */
   public void setMyStage(Stage stage) {
      this.myStage = stage;
   }  

   /**
    * Metodo che imposta il valore da cambiare.<br>
    * Cambia il valore di textTitle e textInserire.<br>
    * @param field valore da cambiare
    */
   public void setValue(String field) {
      textTitle.setText("Cambia " + field);
      textInserire.setText("Inserire " + field.toLowerCase());
      //textField.setText(field);

      myStage.setTitle(textTitle.getText());
   }

   /**
    * Metodo FXML che salva i dati dell'utente in base al campo scelto.<br>
    * Chiama il metodo close per chiudere la stage.<br>
    * @throws java.io.IOException
    */
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
         case "cambia email":
            utenteLoggato.setEmail(newData);
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

   /**
    * Metodo FXML che chiude la stage prima di salvare i dati.<br>
    * @throws java.io.IOException
    */
   @FXML 
   private void indietro() throws java.io.IOException {
      myStage.close();
   }
}
