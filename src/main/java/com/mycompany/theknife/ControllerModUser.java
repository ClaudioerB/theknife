package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

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



   /*public ControllerModUser() {}
   public ControllerModUser(int idRistorante, Recensione recensione) {
      this.recensioneMod = recensione;
      this.idMod = idRistorante;
   }*/
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

      //gestoreDataset = new GestoreDataset();
      gestoreDataset = GestoreDataset.getGestoreDataset();
      filteredList = new ArrayList<>();

      gestore = Gestore.getGestore();
      gestoreUtenti = GestoreUtenti.getGestoreUtenti();
      utenteLoggato = gestore.getUtenteLoggato();
      dataSetFavourite = gestoreUtenti.getFavouriteByUsername(utenteLoggato.getUsername());

      gestoreRecensioni= GestoreRecensioni.getGestoreRecensioni();
      recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());


      setText();

      setRistoratore();

      filter();

      fillListView(filteredList);
      fillRecensioniView(recensioni);
      
   }

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

   private void fillListView(ArrayList<String[]> list) {
        listFavourite.getItems().clear();
        boolean checkfirst = true;
        for (String[] row : list) {
            listFavourite.getItems().add("Ristorante N: "+row[16]+" - Nome: "+row[0] + " - Stato: " + row[2] + " - Città: " + row[3]+ " - Prezzo:" + row[4] + " - Tipo: " + row[5]);
            listFavourite.refresh();
        }
        if (list.isEmpty() || (list.size() == 1 && checkfirst == false)) {
            listFavourite.getItems().add("Nessun ristorante trovato nei preferiti.");
            listFavourite.refresh();
        }
    }

   private void filter() {
    gestoreUtenti = GestoreUtenti.getGestoreUtenti();
    

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
    
    
    int trovati = 0;
    for (String[] row : gestoreDataset.getDataSet()) {
        String rowId = row[16].trim();  
        if (favIdsList.contains(rowId)) {
            filteredList.add(row);
            trovati++;
        }
    }
    
    //System.out.println("Totale ristoranti trovati: " + trovati);
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

   @FXML
    private void visualizzaRistoranteButtonAction() throws IOException {
        String selectedItem = listFavourite.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
            // Nessun elemento selezionato o messaggio di nessun ristorante trovato
            return;
        }
        String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
        ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
        App.setRoot("ViewRistorante");
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
         int lenPsw = utenteLoggato.getPasswordHash().length();
         String stringPsw = "";
         for (int i = 0; i < lenPsw; i++) {
            stringPsw += "*";
         }
         passwordText.setText(stringPsw);
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
   private void rimuoviPreferito() throws IOException {
      String selectedItem = listFavourite.getSelectionModel().getSelectedItem();
      if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
         // Nessun elemento selezionato o messaggio di nessun ristorante trovato
         return;
      }
      String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
      
      String[] ristorante = GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante);
      //gestoreUtenti.rimuoviPreferitoUtente(utenteLoggato.getUsername(),ristorante);
      utenteLoggato.removePreferito(ristorante);
      dataSetFavourite = gestoreUtenti.getFavouriteByUsername(utenteLoggato.getUsername());
      filter();
      fillListView(filteredList);
   }

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
   public void settingRecensione() {
      recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());
      fillRecensioniView(recensioni);
   }
   public void setRecensioneDaCambiare(Recensione rec) {
      vecchiaRecensione = rec;
   }
   public Recensione getRecensioneVecchia() {
      return vecchiaRecensione;
   }

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
            //String idRistorante = null;
            /*for(Recensione rec : recensioni) {
               if(rec.utenteRecensione.equals(UtenteSelezionato)&& rec.getData().equals(DataSelezionata) && rec.getOra().equals(OraSelezionata)) {
                  recensioneS = rec;
                  idRistorante = rec.getId();
                  break;
               }
            }*/
            int idR = 0;
            ArrayList<Recensione> recensioniArr = gestoreRecensioni.getRecensioni();
            for (int i=1; i<recensioniArr.size(); i++) {
               Recensione rec = recensioniArr.get(i);

               if(rec.utenteRecensione.equals(UtenteSelezionato)&& rec.getData().equals(DataSelezionata) && rec.getOra().equals(OraSelezionata)) {
                  recensioneS = rec;
                  //idRistorante = rec.getId();
                  idR = i;
                  break;
               }
            }
            if(recensioneS != null && recensioneS.getRisposta()!= null&& !recensioneS.getRisposta().equals(" ")) {
               System.out.println("Impossibile eliminare la recensione siccome possiede una risposta");
            }
            else if (recensioneS != null && idR != 0) {
               gestoreRecensioni.rimuoviRecensione(idR);
            }
            else if (idR == 0){
               System.out.println("Non esiste la recensione");
            }
            else {
               System.out.println("Non c'è nessuna recensione selezionata");
            }
         /*String selectedItem = listRecensioni.getSelectionModel().getSelectedItem();
         if (selectedItem == null || selectedItem.startsWith("non ci sono recensioni.")) {
         // Nessun elemento selezionato o messaggio di nessun ristorante trovato
         return;
         }*/
         recensioni = gestoreRecensioni.getRecensioniByUsername(utenteLoggato.getUsername());
         fillRecensioniView(recensioni);
      }
   }

   @FXML 
   private void switchRistoratore() throws IOException { 
      App.setRoot("ModRistoratore");
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
