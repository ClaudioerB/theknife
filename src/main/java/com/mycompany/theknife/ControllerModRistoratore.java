package com.mycompany.theknife;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;

/**
 * @author TheKnifeTeam
 * 
 * ControllerModRistoratore rappresenta il controller per la scena ModRistoratore.<br>
 * Si occupa di gestire l'interfaccia di gestione di un ristoratore.<br>
 * Le sue funzioni includono la visualizzazione dei propri ristoranti.<br>
 * Inoltre la possibilità di creare nuovi ristoranti, di visualizzarli, di modificarli e eliminarli.<br>
 * 
 * @version 1.0
 */
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

   /**
     * Metodo FXML che inizializza la scena.<br>
     * Inoltre carica l'icona del knife e anche il dataset dei ristoranti del ristoratore.<br>
     */
   @FXML
   private void initialize() {
      String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
       
        if (!new File(knifePath).exists()) {
            knifePath = System.getProperty("user.dir")
            + "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png";
        }
            knifeImageView.setImage(
                new javafx.scene.image.Image(new File(knifePath).toURI().toString())
        );
        knifeImageView.setVisible(true); 
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

   /**
    * Metodo che esegue i filtri per trovare i ristoranti del ristoratore.<br>
    * Chiama getPersoneRistorantiByIdUtente per ottenere la lista di ristoranti.<br>
    */
   private void filter() {
      ArrayList<String[]> appoggio = new ArrayList<>();
      boolean checkfirst = true;
      String idRistoranti;
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

   /**
    * Metodo FXML che esegue la ricerca dei ristoranti.<br>
    * Chiama fillListView per riempire la ListView con i ristoranti trovati.<br>
    */
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

   /**
    * Metodo che riempie la ListView con i ristoranti.<br>
    * Non visualizza la riga del ristorante se è l'intestazione.<br>
    * Se il ristorante è vuoto viene mostrato il messaggio "Nessun ristorante trovato".<br>
    * @param list lista di ristoranti
    */
   private void fillListView(ArrayList<String[]> list) {
      listRestaurants.getItems().clear();
   
      String deliveryValue, prenotationValue;
      for (String[] row : list) {
         if (!row[0].equals("Name")&&!row[2].equals("State")) {
            deliveryValue = setDeliveryOrPrenotationValue(row[14]);
            prenotationValue = setDeliveryOrPrenotationValue(row[15]);
            String valutazione = String.valueOf(gestoreDataset.calcStelle(row[13]));
            listRestaurants.getItems().add("Ristorante N: "+row[16]+" - Nome: "+row[0] + " - Stato: " + row[2] + " - Città: " + row[3]+ " - Prezzo:" + row[4] + " - Tipo: " + row[5] + " - Consegna: " + deliveryValue + " - Prenotazione: " + prenotationValue + " - Valutazione: " + valutazione);
            listRestaurants.refresh();
         }
      }
      if (list.isEmpty() || (list.size() == 0 )) {
         listRestaurants.getItems().add("Nessun ristorante trovato con i filtri selezionati.");
         listRestaurants.refresh();
      }
   }

   /**
    * Metodo che imposta il valore di consegna o prenotazione.<br>
    * @param value valore da impostare
    */
   private String setDeliveryOrPrenotationValue(String value) {
      if (value.equals("1")) {
         return "Sì";
      } else if (value.equals("0")) {
         return "No";
      }
      return "N/A";
   }
   
   /**
    * Metodo FXML che ritorna alla schermata dei servizi dell'utente.<br>
    * @throws IOException
    */
   @FXML 
   private void switchIndietro() throws IOException {
      App.setRoot("ModUser");
   }
   /**
     * Metodo FXML che visualizza nel dettaglio un ristorante selezionato.<br>
     * Utilizzato per aprire la scena ViewRistorante usando il metodo setRoot della class App.<br>
     * @throws IOException 
     */
   @FXML
   private void visualizzaRistoranteButtonAction() throws IOException {
      String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
      App.setRoot("ViewRistorante");
   }
   /**
    * Metodo FXML che ritorna alla schermata HomeLogged.<br>
    * @throws IOException
    */
   @FXML 
   private void switchToHome() throws IOException {
      App.setRoot("HomeLogged");
   }
   /**
    * Metodo FXML che ritorna alla schermata CreaRistorante.<br>
    * @throws IOException
    */
   @FXML 
   private void addRistorante() throws IOException {
      App.setRoot("CreaRistorante");
   }
   /**
    * Metodo FXML che rimuove un ristorante selezionato.<br>
    * Chiama il metodo rimuovi per rimuovere il ristorante dal dataset.<br>
    * @throws IOException
    */
   @FXML 
   private void rimuoviRistorante() throws IOException {
      rimuovi();
   }
   /**
    * Metodo FXML che aggiorna la ListView con i ristoranti filtrati.<br>
    * @throws IOException
    */
   @FXML 
   private void checkFilteredList() throws IOException {
      filteredList = gestoreDataset.getDataSet();
      searchingButtonAction();
      filter();
      fillListView(filteredList);
   }
   /**
    * Metodo FXML che resetta la ListView con tutti i ristoranti del ristoratore.<br>
    * @throws IOException
    */
   @FXML 
   private void resetButtonAction() throws IOException {
      filteredList = gestoreDataset.getDataSet();
      searchTextField.setText("");
      filter();
      fillListView(filteredList);
   }
   /**
    * Metodo che rimuove il ristorante selezionato.<br>
    * <ul>
    * <li>Chiama il metodo removeRistoranteById per rimuovere il ristorante dal dataset.</li>
    * <li>Chiama il metodo removePersoneRistorantiByIdRistorante per rimuovere il ristorante dal dataset con i proprietari.</li>
    * <li>Chiama il metodo rimuoviPreferitoUtente per rimuovere il ristorante dai preferiti dell'utente.</li>
    * <li>Chiama il metodo filter e fillListView per aggiornare la lista di ristoranti.</li>
    * </ul>
    * @throws IOException
    */
   private void rimuovi() throws IOException {
      String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();

      gestoreDataset.removeRistoranteById(idRistorante);
      gestoreUtenti.removePersoneRistorantiByIdRistorante(idRistorante, utenteLoggato.getId());
      gestoreUtenti.rimuoviPreferitoUtente(utenteLoggato.getUsername(),idRistorante);

      filter(); 
      fillListView(filteredList);
   }
   /**
    * Metodo FXML che ritorna alla schermata ModificaRistorante.<br>
    * @throws IOException
    */
   @FXML 
   private void modifyRistorante() throws IOException {
      String selectedItem = listRestaurants.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), true);
      App.setRoot("ViewRistorante");
   }
}
