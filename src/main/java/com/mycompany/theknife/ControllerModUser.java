package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerModUser {
   
   @FXML
   private javafx.scene.image.ImageView knifeImageView;

   @FXML 
   private javafx.scene.image.ImageView changeIdNome;

   @FXML 
   private javafx.scene.image.ImageView changeIdCognome;

   @FXML 
   private javafx.scene.image.ImageView changeIdCitta;

   @FXML 
   private javafx.scene.image.ImageView changeIdPassword;

   @FXML 
   private javafx.scene.image.ImageView changeIdIndirizzo;

   @FXML
   private javafx.scene.image.ImageView changeIdStato;

   @FXML 
   private javafx.scene.control.Button indietroButton;

   @FXML 
   private javafx.scene.text.Text nomeText;

   @FXML 
   private javafx.scene.text.Text cognomeText;

   @FXML 
   private javafx.scene.text.Text indirizzoText;

   @FXML 
   private javafx.scene.text.Text cittaText;

   @FXML 
   private javafx.scene.text.Text statoText;

   @FXML 
   private javafx.scene.text.Text textTitle;

   @FXML 
   private javafx.scene.text.Text passwordText;

   @FXML 
   private javafx.scene.control.Button salvaButton;

   private Gestore gestore;
   private Utente utenteLoggato;

   @FXML
   private void initialize() {
      String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
            knifeImageView.setVisible(true);
        }
        String changePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/refresh.png";  
        java.io.File changeFile = new java.io.File(changePath);
        if (changeFile.exists()) {       
            changeIdNome.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdNome.setVisible(true);
            changeIdCognome.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdCognome.setVisible(true);
            changeIdCitta.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdCitta.setVisible(true);
            changeIdPassword.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdPassword.setVisible(true);
            changeIdIndirizzo.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdIndirizzo.setVisible(true);
            changeIdStato.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
            changeIdStato.setVisible(true);
        }

      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();
      setText();
   }


   @FXML 
   private void changeNomeData() throws IOException {
      changeData("Nome");
   }
   @FXML
   private void changeCognomeData() throws IOException {
      changeData("Cognome");
   }
   @FXML 
   private void changeCittaData() throws IOException {
      changeData("Città");
   }
   @FXML
   private void changePasswordData() throws IOException {
      changePsw();
   }
   @FXML 
   private void changeIndirizzoData() throws IOException {
      changeData("Indirizzo");
   }
   @FXML
   private void changeStatoData() throws IOException {
      changeData("Stato");
   }

   private void changeData(String field) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDataUser.fxml"));
      Parent root = loader.load();

      Stage smallStage = new Stage();
      smallStage.setScene(new Scene(root, 433, 482));
      //smallStage.setTitle("Finestra Piccola");

      smallStage.initModality(Modality.APPLICATION_MODAL);
      ControllerChangeDataUser controller = loader.getController();
      controller.setMyStage(smallStage);
      controller.setValue(field); // Puoi passare i valori appropriati qui

      smallStage.showAndWait();

      setText();
      setCredenziali();
   }

   private void changePsw() throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePasswordUser.fxml"));
      Parent root = loader.load();

      Stage smallStage = new Stage();
      smallStage.setScene(new Scene(root, 433, 545));
      smallStage.setTitle("Cambiare Password");

      smallStage.initModality(Modality.APPLICATION_MODAL);
      ControllerChangePasswordUser controller = loader.getController();
      controller.setMyStage(smallStage);

      smallStage.showAndWait();

      setText();
      setCredenziali();
   }

   public void setText() {
      gestore = Gestore.getGestore();
      utenteLoggato = gestore.getUtenteLoggato();

      if (utenteLoggato != null) {
         textTitle.setText(utenteLoggato.getUsername());
         nomeText.setText(utenteLoggato.getNome());
         cognomeText.setText(utenteLoggato.getCognome());
         indirizzoText.setText(utenteLoggato.getIndirizzo());
         cittaText.setText(utenteLoggato.getCittà());
         statoText.setText(utenteLoggato.getStato());
         passwordText.setText(utenteLoggato.getPasswordHash());
      } else {
         System.out.println("ATTENZIONE: utenteLoggato è null!");
         return;
      }
   }

   private void setCredenziali() {
      GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      
      String username = utenteLoggato.getUsername();

      String password = utenteLoggato.getPasswordHash();
      String nome = utenteLoggato.getNome();
      String cognome = utenteLoggato.getCognome();
      String indirizzo = utenteLoggato.getIndirizzo();
      String città = utenteLoggato.getCittà();
      String stato = utenteLoggato.getStato();
      
      if (gestoreUtenti.getUtenteByUsername(username) != null) {
         Utente user = gestoreUtenti.getUtenteByUsername(username);
         System.out.println("Aggiornamento credenziali per l'utente: " + username);
         user.setPasswordHash(password);
         user.setNome(nome);
         user.setCognome(cognome);
         user.setIndirizzo(indirizzo);
         user.setCittà(città);
         user.setStato(stato);

         gestoreUtenti.aggiornaUtente(utenteLoggato);
      }
      
   }

   @FXML 
   private void switchHomeNotLogged() throws IOException {
      Gestore.getGestore().setUtenteLoggato(null);
      App.setRoot("HomeNotLogged");
   }

   @FXML 
   private void switchToHome() throws IOException {
      App.setRoot("HomeLogged");
   }
}
