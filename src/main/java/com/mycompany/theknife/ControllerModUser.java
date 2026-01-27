package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerModUser rappresenta il controller per la scena ModUser.<br>
 * Si occupa di gestire l'interfaccia di funzioni che possiede un utente loggato.<br>
 * Le sue funzioni includono la visualizzazione delle proprie recensioni e dei ristoranti preferiti.<br>
 * Inoltre la possibilità di vedere i propri dati e anche modificarli.<br><br>
 * Se l'utente è un ristoratore potrà accedere alla scena per vedere i suoi ristoranti.<br>
 * 
 * @version 1.0
 */
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
   private javafx.scene.image.ImageView changeIdEmail;

   @FXML 
   private javafx.scene.control.Button indietroButton;

   @FXML 
   private javafx.scene.text.Text nomeText;

   @FXML 
   private javafx.scene.text.Text cognomeText;

   @FXML 
   private javafx.scene.text.Text emailText;

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

   @FXML 
   private javafx.scene.control.Button logoutButton2;

   @FXML 
   private javafx.scene.control.Button logoutButton;

   @FXML 
   private javafx.scene.control.Button propriRistoranti;

   @FXML 
   private javafx.scene.control.Button viewRecensione;

   @FXML 
   private javafx.scene.control.Button modifyRecensione;

   @FXML 
   private javafx.scene.control.Button goToRestaurant;

   @FXML
   private javafx.scene.control.ListView<String> listFavourite;

   @FXML
   private javafx.scene.control.ListView<String> listRecensioni;

   private ArrayList<String[]> filteredList;
   private GestoreDataset gestoreDataset;
   private GestoreUtenti gestoreUtenti;

   private Gestore gestore;
   private Utente utenteLoggato;
   private String dataSetFavourite;

   private ArrayList<Recensione> recensioni;
   private GestoreRecensioni gestoreRecensioni;
   private static String[] ristorante;

   private Recensione vecchiaRecensione;
   private ControllerModUser controller;

   private String idRistorante = null;

   /*public ControllerModUser() {}
   public ControllerModUser(int idRistorante, Recensione recensione) {
      this.recensioneMod = recensione;
      this.idMod = idRistorante;
   }*/
   /**
     * Metodo FXML che inizializza la scena.<br>
     * Inoltre carica l'icona del knife e anche il dataset dei ristoranti preferiti e recensioni.<br>
     * Carica anche i dati dell'utente loggato.<br>
     */
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
      }else{
            knifePath=System.getProperty("user.dir")+ "../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
            knifeFile = new java.io.File(knifePath);
            if (knifeFile.exists()) {       
               knifeImageView.setImage(
                        new javafx.scene.image.Image(knifeFile.toURI().toString())
               );
               
               knifeImageView.setVisible(true); 
            }
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
         changeIdEmail.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
         changeIdEmail.setVisible(true);
      }else{
            changePath=System.getProperty("user.dir")+ "/../src/main/java/com/mycompany/theknife/data/refresh.png"; 
            changeFile = new java.io.File(changePath);
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
               changeIdEmail.setImage(new javafx.scene.image.Image(changeFile.toURI().toString()));
               changeIdEmail.setVisible(true);
            }
      }
      gestoreDataset = GestoreDataset.getGestoreDataset();
      filteredList = new ArrayList<>();

      gestore = Gestore.getGestore();
      gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      utenteLoggato = gestore.getUtenteLoggato();
      if (utenteLoggato == null) {
         System.out.println("ERRORE: Nessun utente loggato! Torno alla home.");
         try {
               App.setRoot("HomeNotLogged");
         } catch (IOException e) {
               e.printStackTrace();
         }
         return;
      }
      dataSetFavourite = gestoreUtenti.getFavouriteByUsername(utenteLoggato.getUsername());
      gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
      recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());
      
      setText();
      setRistoratore();
      filter();
      fillListView(filteredList);
      fillRecensioniView(recensioni);
   }
   /**
    * Metodo che riempie la ListView con le recensioni dell'utente.<br>
    * Se non ci sono recensioni, viene mostrato il messaggio "non ci sono recensioni".<br>
    * @param list Lista dell recensioni dell'utente
    */
   public void fillRecensioniView(ArrayList<Recensione> list) {
      listRecensioni.getItems().clear();
      
      for (Recensione row : list) {
         if (row != null) {
            String appoggio ="Titolo: "+row.titolo+" - By: "+row.utenteRecensione + " - Voto: ";
            for(int i=0; i<row.stelle; i++) {
               appoggio += "★";
            }
            appoggio += " - "+row.data+" - "+row.ora+"\n";
            listRecensioni.getItems().add(appoggio);
            listRecensioni.refresh();
         }
      }
      if (list.isEmpty()){
         listRecensioni.getItems().add("non ci sono recensioni.");
         listRecensioni.refresh();
      }
   }

   /**
    * Metodo che setta la visibilità dei pulsanti in base all'utente loggato.<br>
    * Se l'utente è un ristoratore viene mostrato il pulsante per vedere i suoi ristoranti.<br>
    */
   private void setRistoratore() {
      if(utenteLoggato.isRistoratore()) {
         logoutButton2.setVisible(true);
         propriRistoranti.setVisible(true);
         logoutButton.setVisible(false);
      }
      else {
         logoutButton2.setVisible(false);
         propriRistoranti.setVisible(false);
         logoutButton.setVisible(true);
      }
   }

   /**
    * Metodo che riempie la ListView con i ristoranti preferiti dell'utente.<br>
    * Non visualizza la riga del ristorante se è l'intestazione.<br>
    * Se ci sono ristoranti preferiti, viene mostrato il numero di ristoranti preferiti.<br>
    * Se non ci sono ristoranti preferiti, viene mostrato il messaggio "Nessun ristorante trovato nei preferiti".<br>
    * @param list Lista dei ristoranti preferiti dell'utente
    */
   private void fillListView(ArrayList<String[]> list) {
      listFavourite.getItems().clear();
      for (String[] row : list) {
         if (row[0].equals("Name") && row[2].equals("State")) {
            continue;
         }
         listFavourite.getItems().add("Ristorante N: " + row[16] + " - Nome: " + row[0] + " - Stato: " + row[2] + " - Città: " + row[3] + " - Prezzo: " + row[4] + " - Tipo: " + row[5]);
      }
      if (listFavourite.getItems().isEmpty()) {
         listFavourite.getItems().add("Nessun ristorante trovato nei preferiti.");
      }
      listFavourite.refresh();
   }

   /**
    * Metodo che filtra i ristoranti in base ai preferiti dell'utente.<br>
    */
   private void filter() {
      gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      dataSetFavourite = gestoreUtenti.getFavouriteByUsername(utenteLoggato.getUsername());
      if (dataSetFavourite == null || dataSetFavourite.isEmpty()) {
         filteredList.clear();
         return;
      }
      String[] favouriteIds = dataSetFavourite.split(",");
      ArrayList<String> favIdsList = new ArrayList<>();
      for (String id : favouriteIds) {
         String trimmedId = id.trim();
         favIdsList.add(trimmedId);
      }
      filteredList.clear();
      for (String[] row : gestoreDataset.getDataSet()) {
         String rowId = row[16].trim();  
         if (favIdsList.contains(rowId)) {
            filteredList.add(row);
         }
      }
   }

   /**
    * Metodo FXML che gestisce la modifica del nome dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Nome".<br>
    * @throws IOException
    */
   @FXML 
   private void changeNomeData() throws IOException {
      changeData("Nome", false);
   }
   /**
    * Metodo FXML che gestisce la modifica del cognome dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Cognome".<br>
    * @throws IOException
    */
   @FXML
   private void changeCognomeData() throws IOException {
      changeData("Cognome", false);
   }
   /**
    * Metodo FXML che gestisce la modifica della città dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Città".<br>
    * @throws IOException
    */
   @FXML 
   private void changeCittaData() throws IOException {
      changeData("Citta", false);
   }
   /**
    * Metodo FXML che gestisce la modifica della password dell'utente.<br>
    * Chiama il metodo changePsw() per cambire password.<br>
    * @throws IOException
    */
   @FXML
   private void changePasswordData() throws IOException {
      changePsw();
   }
   /**
    * Metodo FXML che gestisce la modifica dell'indirizzo dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Indirizzo".<br>
    * @throws IOException
    */
   @FXML 
   private void changeIndirizzoData() throws IOException {
      changeData("Indirizzo", false);
   }
   /**
    * Metodo FXML che gestisce la modifica dello stato dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Stato".<br>
    * @throws IOException
    */
   @FXML
   private void changeStatoData() throws IOException {
      changeData("Stato", false);
   }
   /**
    * Metodo FXML che gestisce la modifica dell'email dell'utente.<br>
    * Chiama il metodo changeData passando come parametro "Email".<br>
    * @throws IOException
    */
   @FXML 
   private void changeEamilData() throws IOException {
      changeData("Email", false);
   }

   /**
    * Metodo che gestisce la modifica di un campo dell'utente.<br>
    * Apre una nuova finestra cambiando il campo da modificare.<br>
    * Infine cambia i dati dell'utente e riempie la ListView.<br>
    * @param field Campo dell'utente da modificare
    */
   private void changeData(String field, boolean error) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeDataUser.fxml"));
      Parent root = loader.load();
      Stage smallStage = new Stage();
      smallStage.setScene(new Scene(root, 433, 482));
      smallStage.initModality(Modality.APPLICATION_MODAL);
      ControllerChangeDataUser controller = loader.getController();
      controller.setMyStage(smallStage);
      controller.setValue(field, error);
      smallStage.showAndWait();

      setText();
      String stato = (statoText.getText() == null) ? "" : statoText.getText().trim();
      String citta = (cittaText.getText() == null) ? "" : cittaText.getText().trim();
      
      if (citta.isEmpty()) {
         System.out.println("Città vuota: impossibile procedere");
         return;
      }
      
      int validazione = gestoreDataset.validaStatoCitta(citta, stato);
      
      if (validazione == 0) {
         String statoTrovato = gestoreDataset.findStatoByCitta(citta);
         if (statoTrovato == null || statoTrovato.isEmpty()) {
               gestoreDataset.addNewStato(stato);
               gestoreDataset.addNewCitta(stato, citta);
         }
         setCredenziali();
      } else if (validazione == 1) {
         if (field.equals("Stato")) {
               String statoCorretto = gestoreDataset.findStatoByCitta(citta);
               utenteLoggato.setStato(statoCorretto);
               statoText.setText(statoCorretto);
               cittaText.setText("");
               utenteLoggato.setCittà("");
               setCredenziali();
               changeData(field, true);
         } else if (field.equals("Citta")) {
               changeData(field, true);
         }
      }
   }
   
   /**
    * Metodo che gestisce la modifica della password dell'utente.<br>
    * Apre una nuova finestra per cambiare la password.<br>
    * Infine cambia i dati dell'utente e li salva nel dataset.<br>
    * @throws IOException
    */
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

   /**
     * Metodo che gestisce l'azione del pulsante "Visualizza Ristorante".<br>
     * Visualizza nel dettaglio il ristorante selezionato.<br>
     * @throws IOException
     */
   @FXML
   private void visualizzaRistoranteButtonAction() throws IOException {
      String selectedItem = listFavourite.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
      App.setRoot("ViewRistorante");
   }

   /**
    * Metodo che riempie i campi con i dati dell'utente.<br>
    */
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
         emailText.setText(utenteLoggato.getEmail());
         int lenPsw = utenteLoggato.getPasswordHash().length();
         String stringPsw = "";
         for (int i = 0; i < lenPsw; i++) {
            stringPsw += "*";
         }
         passwordText.setText(stringPsw);
         verificaECreaStatoCitta(cittaText.getText(), statoText.getText());
      } else {
         System.out.println("ATTENZIONE: utenteLoggato è null!");
         return;
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
    * Metodo che aggiorna i dati dell'utente.<br>
    */

   private void setCredenziali() {
      GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      
      String username = utenteLoggato.getUsername();

      String password = utenteLoggato.getPasswordHash();
      String email = utenteLoggato.getEmail();
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
         user.setEmail(email);
         user.setIndirizzo(indirizzo);
         user.setCittà(città);
         user.setStato(stato);
         gestoreUtenti.aggiornaUtente(utenteLoggato);
      }
   }

   /**
    * Metodo che rimuove un ristorante dai preferiti dell'utente.<br>
    * Chiama il metodo rimuoviPreferitoUtente per rimuovere il ristorante selezionato.<br>
    * Chiama i filtri e riempie la ListView.<br>
    * @throws IOException
    */
   @FXML 
   private void rimuoviPreferito() throws IOException {
      String selectedItem = listFavourite.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      
      String[] ristorante = GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante);
      
      utenteLoggato.removePreferito(ristorante);
      dataSetFavourite = gestoreUtenti.getFavouriteByUsername(utenteLoggato.getUsername());
      filter();
      fillListView(filteredList);
   }

   /**
    * Metodo che visualizza un ristorante dalla recensione dell'utente selezionata.<br>
    * Visualizza nel dettaglio il ristorante selezionato.<br>
    * Se non trova il ristorante, visualizza un messaggio di errore.<br>
    * @throws IOException
    */
   @FXML 
   private void switchToRistorante() throws IOException {
      String selectedRecensione = (String) listRecensioni.getSelectionModel().getSelectedItem();
      if(selectedRecensione != null && !selectedRecensione.equals("non ci sono recensioni.")) {
         String[] partiRecensione = selectedRecensione.split(" - ");
            String titoloSelezionato = partiRecensione[0].replace("Titolo: ", "").trim();
            String UtenteSelezionato = partiRecensione[1].replace("By: ", "").trim();
            String DataSelezionata = partiRecensione[3].trim();
            String OraSelezionata = partiRecensione[4].trim();

            Recensione recensioneS = null;
            String idRistorante = null;
            for(Recensione rec : recensioni) {
               if(rec.utenteRecensione.equals(UtenteSelezionato)&& rec.getData().equals(DataSelezionata) && rec.getOra().equals(OraSelezionata)) {
                  recensioneS = rec;
                  idRistorante = rec.getId();
                  break;
               }
            }
            if (recensioneS != null) {
               ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
               App.setRoot("ViewRistorante");
            }
            else {
               System.out.println("Non c'è nessuna recensione selezionata");
            }
         
      }
   }

   /**
    * Metodo che visualizza la recensione dell'utente selezionata.<br>
    * Visualizza nel dettaglio la recensione selezionata.<br>
    * Se la recensione non esiste, visualizza un messaggio di errore.<br>
    * @throws IOException
    */
   @FXML 
   private void visualizzaRecensioneButtonAction() throws IOException {
      String selectedRecensione = (String) listRecensioni.getSelectionModel().getSelectedItem();
      if(selectedRecensione != null && !selectedRecensione.equals("non ci sono recensioni.")) {
         String[] partiRecensione = selectedRecensione.split(" - ");
         String titoloSelezionato = partiRecensione[0].replace("Titolo: ", "").trim();
         String UtenteSelezionato = partiRecensione[1].replace("By: ", "").trim();
         String DataSelezionata = partiRecensione[3].trim();
         String OraSelezionata = partiRecensione[4].trim();
         Recensione recensioneDaVisualizzare = null;
         for(Recensione rec : recensioni) {
            if(rec.utenteRecensione.equals(UtenteSelezionato)&& rec.getData().equals(DataSelezionata) && rec.getOra().equals(OraSelezionata)) {
               recensioneDaVisualizzare = rec;
               setRecensioneDaCambiare(rec);
               setIdRistorante(rec.getId());
               break;
            }
         }
         if(recensioneDaVisualizzare != null && recensioneDaVisualizzare.getRisposta()!= null&& !recensioneDaVisualizzare.getRisposta().equals(" ")) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("VisualizzaRecensione.fxml"));
            Parent root = loader.load();
            ControllerVisualizzaRecensione controller = loader.getController();
            controller.setRecensione(recensioneDaVisualizzare);
            Stage stage = new Stage();
            stage.setTitle("Visualizza Recensione");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.show(); 
         }
         else if (recensioneDaVisualizzare != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("VisualizzaModRecensioneSenzaRisposta.fxml"));
            Parent root = loader.load();
            ControllerModVisualizzaRecensioneSenzaRisposta controller = loader.getController();
            controller.setRecensione(recensioneDaVisualizzare);
            controller.setController(this);
            Stage stage = new Stage();
            stage.setTitle("Visualizza Oppure Modifica Recensione");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.show(); 
         } 
         else {
            System.out.println("Recensione non trovata.");
         } 
      }
   }
   /**
    * Metodo per impostare l'id del ristorante.<br>
    * @param id Id del ristorante
    */
   public void setIdRistorante(String id) {
      idRistorante = id;
   }
   /**
    * Metodo per ottenere l'id del ristorante.<br>
    * @return Id del ristorante
    */
   public String getIdRistorante() {
      return idRistorante;
   }
   /**
    * Metodo che imposta le recensioni dell'utente loggato.<br>
    * Chiama fillRecensioniView per visualizzare le recensioni selezionate.<br>
    */
   public void settingRecensione() {
      recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());
      fillRecensioniView(recensioni);
   }
   /**
    * Metodo per impostare la recensione da cambiare.<br>
    * @param rec Recensione da cambiare
    */
   public void setRecensioneDaCambiare(Recensione rec) {
      vecchiaRecensione = rec;
   }
   /**
    * Metodo per ottenere la recensione vecchia.<br>
    * @return Recensione vecchia
    */
   public Recensione getRecensioneVecchia() {
      return vecchiaRecensione;
   }

   /**
    * Metodo per rimuovere una recensione.<br>
    * Chiama il metodo rimuoviRecensioneper eliminare la recensione dal dataset.<br>
    * Chiama il metodo removeStelle per rimuovere la valutazione dal ristorante.<br>
    * Infine chiama fillRecensioniView per visualizzare le recensioni aggiornate dopo la rimozione.<br>
    * @throws IOException 
    */
   @FXML
   private void rimuoviRecensione() throws IOException {
      String selectedRecensione = (String) listRecensioni.getSelectionModel().getSelectedItem();
      if(selectedRecensione != null && !selectedRecensione.equals("non ci sono recensioni.")) {
         String[] partiRecensione = selectedRecensione.split(" - ");
            String titoloSelezionato = partiRecensione[0].replace("Titolo: ", "").trim();
            String UtenteSelezionato = partiRecensione[1].replace("By: ", "").trim();
            String DataSelezionata = partiRecensione[3].trim();
            String OraSelezionata = partiRecensione[4].trim();

            Recensione recensioneS = null;
            String idRistorante = null;
            int idR = -1;
            ArrayList<Recensione> recensioniArr = gestoreRecensioni.getRecensioni();
            String rating = null;

            for (int i = 0; i < recensioniArr.size(); i++) {
               Recensione rec = recensioniArr.get(i);
               if (rec.utenteRecensione.equals(UtenteSelezionato) && 
                  rec.getData().equals(DataSelezionata) && 
                  rec.getOra().equals(OraSelezionata)) {
                  recensioneS = rec;
                  idRistorante = rec.getId();
                  rating = String.valueOf(rec.getStelle());
                  idR = i;
                  break;
               }
            }
            if (recensioneS != null && recensioneS.getRisposta() != null && 
               !recensioneS.getRisposta().equals(" ")) {
               System.out.println("Impossibile eliminare la recensione siccome possiede una risposta");
            } else if (idR >= 0 && idRistorante != null) {
               gestoreRecensioni.rimuoviRecensione(idR);
               gestoreDataset.removeStelle(rating, idRistorante);
            } else {
               System.out.println("Recensione non trovata");
            }
         recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());
         fillRecensioniView(recensioni);
      }
   }

   /**
    * Metodo per ritornare alla schermata ModRistoratore.<br>
    * @throws IOException
    */
   @FXML 
   private void switchRistoratore() throws IOException { 
      App.setRoot("ModRistoratore");
   }

   /**
    * Metodo per ritornare alla schermata HomeNotLogged.<br>
    * @throws IOException
    */
   @FXML 
   private void switchHomeNotLogged() throws IOException {
      Gestore.getGestore().setUtenteLoggato(null);
      App.setRoot("HomeNotLogged");
   }

   /**
    * Metodo per ritornare alla schermata HomeLogged.<br>
    * @throws IOException
    */
   @FXML 
   private void switchToHome() throws IOException {
      App.setRoot("HomeLogged");
   }
}
