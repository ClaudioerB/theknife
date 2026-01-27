package com.mycompany.theknife;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

   @FXML 
   private Label errorLabel;

   private Gestore gestore;
   private Utente utenteLoggato;
   private Stage myStage;
   private GestoreDataset gestoreDataset;
   private boolean controllo;

   /**
    * Metodo FXML che inizializza il controller e imposta i valori iniziali.<br>
    * Inoltre nasconde la label di errore.<br>
    */
   @FXML 
   private void initialize() {
      gestore = Gestore.getGestore();
      gestoreDataset = GestoreDataset.getGestoreDataset();
      utenteLoggato = gestore.getUtenteLoggato();
      textField.setText("");
      errorLabel.setVisible(false);
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
   public void setValue(String field, boolean error) {
      textTitle.setText("Cambia " + field);
      textInserire.setText("Inserire " + field.toLowerCase());
      //textField.setText(field);
      myStage.setTitle(textTitle.getText());
      errorLabel.setVisible(error);
   }

   /**
    * Metodo FXML che salva i dati dell'utente in base al campo scelto.<br>
    * Mostra un messaggio di errore se il campo non viene modificato.<br>
    * Chiama il metodo close per chiudere la stage.<br>
    * @throws java.io.IOException
    */
   @FXML 
   private void saveData() throws java.io.IOException {
      boolean check = false;
      String newData = textField.getText();
      String fieldToChange = textTitle.getText().toLowerCase().trim();
      errorLabel.setVisible(false);
      
      if (fieldToChange.equals("cambia citta") || fieldToChange.equals("cambia stato")) {
         String citta = fieldToChange.equals("cambia citta") ? newData : utenteLoggato.getCittà();
         String stato = fieldToChange.equals("cambia stato") ? newData : utenteLoggato.getStato();
         
         GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
         int validazione = gestoreDataset.validaStatoCitta(citta, stato);
         
         if (validazione == 1) {
               String statoEsistente = gestoreDataset.findStatoByCitta(citta);
               errorLabel.setText("Errore: " + citta + " esiste in " + statoEsistente);
               errorLabel.setVisible(true);
               return;
         }
      }
      
      switch (fieldToChange) {
         case "cambia nome":
               utenteLoggato.setNome(newData);
               check = true;
               break;
         case "cambia cognome":
               utenteLoggato.setCognome(newData);
               check = true;
               break;
         case "cambia indirizzo":
               utenteLoggato.setIndirizzo(newData);
               check = true;
               break;
         case "cambia citta":
               utenteLoggato.setCittà(newData);
               check = true;
               break;
         case "cambia stato":
               utenteLoggato.setStato(newData);
               check = true;
               break;
         case "cambia username":
               utenteLoggato.setUsername(newData);
               check = true;
               break;
         case "cambia email":
               utenteLoggato.setEmail(newData);
               check = true;
               break;
         default:
               System.out.println("Errore: campo non riconosciuto.");
               break;
      }
      
      if (check) {
         myStage.close();
      } else {
         errorLabel.setVisible(true);
      }
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
