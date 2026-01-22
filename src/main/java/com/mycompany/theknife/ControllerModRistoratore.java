package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
public class ControllerModRistoratore {
   @FXML
   private javafx.scene.image.ImageView knifeImageView;

   @FXML 
   private javafx.scene.text.Text textTitle;

   @FXML 
   private javafx.scene.control.Button viewRistoranti;

   @FXML 
   private javafx.scene.control.Button addRistoranti;

   @FXML 
   private javafx.scene.control.Button modifyRistoranti;

   @FXML 
   private javafx.scene.control.Button indietroButton;

   @FXML
   private javafx.scene.control.ListView<String> listRestaurant;

   private ArrayList<String[]> filteredList;
   private GestoreDataset gestoreDataset;
   private GestoreUtenti gestoreUtenti;
   private Gestore gestore;
   private Utente utenteLoggato;
   private String dataSetFavourite;

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
   }

   @FXML 
   private void switchIndietro() throws IOException {
      App.setRoot("ModUser");
   }
   @FXML 
   private void visualizzaRistoranteButtonAction() throws IOException {
      return;
   }
   @FXML 
   private void switchToHome() throws IOException {
      App.setRoot("HomeLogged");
   }
}
