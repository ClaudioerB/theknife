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
   private javafx.scene.control.Button resetButton;

   @FXML 
   private javafx.scene.control.Button searchButton;

   @FXML
   private javafx.scene.control.TextField searchTextField;

   @FXML
   private javafx.scene.control.ListView<String> listRestaurants;

   private ArrayList<String[]> filteredList;
   private ArrayList<String[]> personeRistoranti;
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
      filteredList = GestoreDataset.getDataSet();
      gestore = Gestore.getGestore();
      gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      gestoreDataset = GestoreDataset.getGestoreDataset();
      //personeRistoranti = GestoreUtent
      utenteLoggato = gestore.getUtenteLoggato();
      textTitle.setText(utenteLoggato.getUsername());
      filter();
      fillListView(filteredList);
   }

   private void filter() {
      ArrayList<String[]> appoggio = new ArrayList<>();
      boolean checkfirst = true;
      String idRistoranti;

      /*for (String[] row : filteredList) {
         if (checkfirst) {
            checkfirst = false;
         } else {
            idRistoranti = gestoreUtenti.getPersoneRistorantiByIdUtente(utenteLoggato.getId());
            //System.out.println(idRistoranti);
            if (idRistoranti.contains(row[16])) {
               appoggio.add(row);
            }
         }
      }*/
      String idUtente;
      for (String[] row : filteredList) {
         if (checkfirst) {
            checkfirst = false;
         } else {
            idUtente = gestoreUtenti.getPersoneRistorantiByIdRistorante(row[16]);
            //System.out.println(idRistoranti);
            if (idUtente != null) {
               if (idUtente.equals(utenteLoggato.getId())) {
                  appoggio.add(row);
               } 
            } else {
               System.out.println("Id utente null");
            }
         }
      }
      filteredList = appoggio;
   }

   @FXML 
   private void searchingButtonAction() {
      String searchText = searchTextField.getText().toLowerCase();
      listRestaurants.getItems().clear();
      boolean checkfirst = true;
      ArrayList<String[]> tempList = new ArrayList<>();
      ArrayList<String[]> tempList2 = new ArrayList<>(filteredList);
      for (String[] row : filteredList) {
            if (checkfirst) {
               checkfirst = false;
            } else {
               String nome = row[0].toLowerCase();
               String stato = row[2].toLowerCase();
               String citta = row[3].toLowerCase();
               String prezzo = row[4].toLowerCase();
               String tipo = row[5].toLowerCase();

               if (!(nome.contains(searchText) || stato.contains(searchText) ||
                  citta.contains(searchText) || prezzo.contains(searchText) ||
                  tipo.contains(searchText))) {
                        //tempList.add(row);
                        tempList2.remove(row);
                        //listViewRestaurants.getItems().add("Nome: " + row[0] + " - Stato: " + row[2] + " -Città: " + row[3] + " -Prezzo:" + row[4] + " -Tipo: " + row[5]);
               }
            }
      }
      filteredList = tempList2;
   }

    private void fillListView(ArrayList<String[]> list) {
        listRestaurants.getItems().clear();
        
        String deliveryValue, prenotationValue;
        for (String[] row : list) {
            if (!row[0].equals("Name")&&!row[2].equals("State")) {
                
                deliveryValue = setDeliveryOrPrenotationValue(row[14]);
                prenotationValue = setDeliveryOrPrenotationValue(row[15]);

                listRestaurants.getItems().add("Ristorante N: "+row[16]+" - Nome: "+row[0] + " - Stato: " + row[2] + " - Città: " + row[3]+ " - Prezzo:" + row[4] + " - Tipo: " + row[5] + " - Consegna: " + deliveryValue + " - Prenotazione: " + prenotationValue + " - Valutazione: " + row[13]);
                listRestaurants.refresh();
            }
        }
        if (list.isEmpty() || (list.size() == 0 )) {
            listRestaurants.getItems().add("Nessun ristorante trovato con i filtri selezionati.");
            listRestaurants.refresh();
        }
    }

   private String setDeliveryOrPrenotationValue(String value) {
        if (value.equals("1")) {
            return "Sì";
        } else if (value.equals("0")) {
            return "No";
        }
        return "N/A";
    }
   
   @FXML 
   private void switchIndietro() throws IOException {
      App.setRoot("ModUser");
   }
   @FXML
    private void visualizzaRistoranteButtonAction() throws IOException {
        String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
            // Nessun elemento selezionato o messaggio di nessun ristorante trovato
            return;
        }
        String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
        ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
        App.setRoot("ViewRistorante");
    }
   @FXML 
   private void switchToHome() throws IOException {
      App.setRoot("HomeLogged");
   }
   @FXML 
   private void addRistorante() throws IOException {
      App.setRoot("CreaRistorante");
   }
   @FXML 
   private void rimuoviRistorante() throws IOException {
      rimuovi();
   }
   @FXML 
   private void checkFilteredList() throws IOException {
      filteredList = gestoreDataset.getDataSet();
      searchingButtonAction();
      filter();
      fillListView(filteredList);
   }
   
   @FXML 
   private void resetButtonAction() throws IOException {
      filteredList = gestoreDataset.getDataSet();
      searchTextField.setText("");
      filter();
      fillListView(filteredList);
   }

   private void rimuovi() throws IOException {
      String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         // Nessun elemento selezionato o messaggio di nessun ristorante trovato
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      
      //String[] ristorante = GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante);
      //gestoreUtenti.rimuoviPreferitoUtente(utenteLoggato.getUsername(),ristorante);

      gestoreDataset.removeRistoranteById(idRistorante);
      gestoreUtenti.removePersoneRistorantiByIdRistorante(idRistorante, utenteLoggato.getId());

      filter(); 
      fillListView(filteredList);
   }
   @FXML 
   private void modifyRistorante() throws IOException {
      String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         // Nessun elemento selezionato o messaggio di nessun ristorante trovato
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), true);
      App.setRoot("ViewRistorante");
   }
}
