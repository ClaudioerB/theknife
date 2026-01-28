package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerViewRistorante rappresenta il controller per la scena ViewRistorante.<br>
 * Si occupa di gestire l'interfaccia di visualizzazione di un ristorante specifico.<br>
 * Le sue funzioni includono la visualizzazione nel dettaglio dei ristoranti, la visualizzazione delle recensioni e scriverla se l'utente è loggato.
 * Inoltre la possibilità di selezionarlo come preferito da parte dell'utente loggato.<br>
 * <br>
 * Invece se l'utente è il proprietario del ristorante, viene mostrata la possibilità di modificare i parametri del ristorante.<br>
 * 
 * @version 1.0
 */
public class ControllerViewRistorante {
    @FXML
    private javafx.scene.control.TextField nomeRistoranteField;
    @FXML
    private TextField indirizzoRistoranteField;
		@FXML
    private Button rispondiButton;
    @FXML
    private TextField telefonoRistoranteField;
    @FXML
    private ListView tipoCucinaRistoranteListView;
    @FXML
    private TextField sitoWebRistoranteField;
    @FXML
    private TextField cittaRistoranteField;
    @FXML
    private TextField statoRistoranteField;
    @FXML
    private TextField awardField;
    @FXML
    private TextField serviziTextField;
    @FXML
    private MenuButton prezzoMenuButton;
    @FXML
    private CheckMenuItem prezzo1CheckMenuItem;
    @FXML
    private CheckMenuItem prezzo2CheckMenuItem;
    @FXML
    private CheckMenuItem prezzo3CheckMenuItem;
    @FXML
    private CheckMenuItem prezzo4CheckMenuItem;
    @FXML
    private TextArea descrizioneRistoranteTextArea;
    @FXML
    private ListView recensioniRistoranteListView;
    @FXML
    private ListView serviziRistoranteListView;
    @FXML
    private CheckBox prenotazioniCheckBox;
    @FXML
    private CheckBox consegnaCheckBox;
    @FXML
    private Rating ratingRistorante;
    @FXML
    private Button visualizzaRecensioneButton;
    @FXML
    private ImageView theKnifeImageView;
    @FXML
    private Button modificaServiziButton;
    @FXML
    private Button rimuoviCucineButton;
    @FXML
    private Button rimuoviServiziButton;
    @FXML
    private Button aggiungiRecensioneButton;
    @FXML
    private Button aggiungiPreferitiButton;
    @FXML
    private Button rimuoviPreferitiButton;
    @FXML
    private Button salvaButton;
    @FXML 
    private javafx.scene.control.CheckMenuItem tutteItem;
    @FXML
    private javafx.scene.control.MenuButton cucineFilterComboBox;
    @FXML
    private javafx.scene.control.Label errorFieldcampiVuotiLabel;

    @FXML
    private javafx.scene.control.Label errorStatoCittaLabel;
    
    private static String[] ristorante;
    private ArrayList<Recensione> recensioni;
    private String[] servizi;
    private String[] tipiCucina;
    private GestoreRecensioni gestoreRecensioni;
    private GestoreDataset gestoreDataset;
    private static boolean Modifica;
    private static ControllerViewRistorante instance = null;
    private String prz;
    private String serviziAdd;
    private ArrayList<String> cucineArrayList;
    ArrayList<javafx.scene.control.CheckMenuItem> checkMenuItemsList;
    /**
     * Costruttore di ControllerViewRistorante vuoto.
     */
    public ControllerViewRistorante() {}
    /**
     * Costruttore di ControllerViewRistorante.<br>
     * Inizializza le informazioni del ristorante da visualizzare e se l'utente può modificare le informazioni.<br>
     * @param ristoranteInfo array di stringhe che contiene le informazioni del ristorante da visualizzare.
     * @param modifica booleano che indica se il ristorante può modificare le informazioni.
     */
    private ControllerViewRistorante(String[] ristoranteInfo, boolean modifica) {
        ristorante = ristoranteInfo;
        this.Modifica = modifica;
    }
    /**
     * Metodo per ottenere l'istanza di ControllerViewRistorante.<br>
     * @param ristoranteInfo array di stringhe che contiene le informazioni del ristorante da visualizzare.
     * @param modifica booleano che indica se il ristorante puo' modificare le informazioni.
     * @return istanza di ControllerViewRistorante
     */
    public static ControllerViewRistorante getInstance(String[] ristoranteInfo, boolean modifica) {
        if(instance == null)
            instance =new ControllerViewRistorante(ristoranteInfo, modifica);
        else {
            ristorante = ristoranteInfo;
            Modifica = modifica;
        }
        return instance;
    }
    /**
     * Metodo per inizializzare le informazioni del ristorante da visualizzare.<br>
     * Inoltre imposta anche le azioni che possono essere eseguite dall'utente e quali invece non possono.<br>
     * Mostra possibilmente anche i filtri per la modifica del ristorante.<br>
     */
    public void initialize() {
        gestoreRecensioni= GestoreRecensioni.getGestoreRecensioni();
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
        gestoreDataset = GestoreDataset.getGestoreDataset();
        setServizi(); 
        setTipiCucina();
        errorFieldcampiVuotiLabel.setVisible(false);
        errorStatoCittaLabel.setVisible(false);
        nomeRistoranteField.setText(ristorante[0]);
        indirizzoRistoranteField.setText(ristorante[1]);
        telefonoRistoranteField.setText(ristorante[8]); 
        statoRistoranteField.setText(ristorante[2]);
        sitoWebRistoranteField.setText(ristorante[9]);
        cittaRistoranteField.setText(ristorante[3]);
        awardField.setText(ristorante[10]);
        fillServiziListView();
        descrizioneRistoranteTextArea.setText(ristorante[12]);
        cucineArrayList=new ArrayList<String>();
        
        for (String cucina : tipiCucina) {
            if (!cucina.trim().isEmpty()) {
                cucineArrayList.add(cucina.trim());
            }
        }
        if (checkMenuItemsList == null || checkMenuItemsList.isEmpty()) {
            checkMenuItemsList = new ArrayList<javafx.scene.control.CheckMenuItem>();
            popolaMenuCucineConRadio();
        } else {
            aggiornaSelezioneCucine();
        }
        tipoCucinaRistoranteListView.getItems().clear();
        tipoCucinaRistoranteListView.getItems().addAll(cucineArrayList);
        fillListView(recensioni);
        prz = ristorante[4];
        ratingRistorante.setRating(gestoreDataset.calcStelle(ristorante[13]));
        
        if(ristorante[15].equals("1")) {
            prenotazioniCheckBox.setSelected(true);
        } else {
            prenotazioniCheckBox.setSelected(false);
        }
        if(ristorante[14].equals("1")) {
            consegnaCheckBox.setSelected(true);
        } else {
            consegnaCheckBox.setSelected(false);
        }
        setPrezzo();
        isEditable();
        theKnifeImageViewSet();
        if(isLogged() && !Modifica ){
            isInPreferiti();
            if(isProprietario()&&!recensioni.isEmpty()){
                rispondiButton.setDisable(false);
                rispondiButton.setVisible(true);
            }
            rispondiButton.setDisable(true);
            rispondiButton.setVisible(false);
        } else {
            aggiungiPreferitiButton.setDisable(true);
            rimuoviPreferitiButton.setDisable(true);
            aggiungiPreferitiButton.setVisible(false);
            rimuoviPreferitiButton.setVisible(false);
						rispondiButton.setDisable(true);
            rispondiButton.setVisible(false);
        }
				try {
            toHome();
        } catch (Exception e) {
						System.out.println("errore sull'utente");
        }
    }
    
    /**
     * Metodo per verificare se l'utente loggato e' proprietario del ristorante.<br>
     * Chiama il metodo getPersoneRistorantiByIdRistorante del GestoreUtenti per verificare autenticità.<br>
     * @return true se l'utente loggato e' proprietario del ristorante
     */
    private boolean isProprietario() {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        GestoreUtenti gestoreUtenti =GestoreUtenti.getGestoreUtenti();
        if(gestoreUtenti.getPersoneRistorantiByIdRistorante(ristorante[16]).equals(utenteLoggato.getId())){
            System.out.println(gestoreUtenti.getPersoneRistorantiByIdRistorante(ristorante[16])+"   "+utenteLoggato.getId());
            return true;
        }else{
            return false;
        }
    }
    /**
     * Metodo per aggiornare la selezione delle cucine nel menu.<br>
     */
    private void aggiornaSelezioneCucine() {
        for (javafx.scene.control.CheckMenuItem checkItem : checkMenuItemsList) {
            checkItem.setSelected(cucineArrayList.contains(checkItem.getText()));
        }
    }
    /**
     * Metodo per popolare il menu con le cucine.<br>
     * Chiama il metodo checkFilteredList per aggiornare la selezione delle cucine nel menu.<br>
     */
    private void popolaMenuCucineConRadio() {
        cucineFilterComboBox.getItems().clear();

        tutteItem = new javafx.scene.control.CheckMenuItem("Tutte le cucine");
        tutteItem.setSelected(true);
        tutteItem.setId("tutteCucine");
        
        cucineFilterComboBox.getItems().add(tutteItem);

        cucineFilterComboBox.getItems().add(new javafx.scene.control.SeparatorMenuItem());

        boolean checkfirst = true;
        for (String row : GestoreDataset.getDataSetCucina()) {
            if (checkfirst) {
                checkfirst = false;
                continue;
            }
            
            javafx.scene.control.CheckMenuItem checkItem = new javafx.scene.control.CheckMenuItem(row);
            checkMenuItemsList.add(checkItem);
                checkItem.setOnAction(e -> {
                checkFilteredList(checkItem);
            });
            cucineFilterComboBox.getItems().add(checkItem);
        }
    }
    
    /**
     * Metodo per verificare se una cucina e' stata selezionata.<br>
     * Chiama il metodo checkFilteredList per aggiornare la selezione delle cucine nel menu.<br>
     * @param checkItem CheckMenuItem della cucina selezionata
     */
    private void checkFilteredList(javafx.scene.control.CheckMenuItem checkItem) {
        if (checkItem.isSelected()) {
            if (!cucineArrayList.contains(checkItem.getText())) {
                cucineArrayList.add(checkItem.getText());
            }
        } else {
            cucineArrayList.remove(checkItem.getText());
        }
        fillCucineListView();
    }
    /**
     * Metodo per riempire la ListView con i servizi del ristorante.<br>
     * Se non ci sono servizi, viene mostrato il messaggio "Non é presente alcun servizio".<br>
     */
    private void fillServiziListView() {
        serviziRistoranteListView.getItems().clear();
        if (servizi == null || servizi.length == 0 || (servizi.length == 1 && servizi[0].trim().isEmpty())) {
            serviziRistoranteListView.getItems().add("Non è presente alcun servizio");
        } else {
            for (String servizio : servizi) {
                if (!servizio.trim().isEmpty()) {
                    serviziRistoranteListView.getItems().add(servizio);
                }
            }
        }
        serviziRistoranteListView.refresh();
    }
    /**
     * Metodo per riempire la ListView con le cucine del ristorante.<br>
     * Se non ci sono cucine, viene mostrato il messaggio "Nessuna cucina aggiunta".<br>
     */
    private void fillCucineListView(){
        tipoCucinaRistoranteListView.getItems().clear();
        
        for (String row : cucineArrayList) {
            tipoCucinaRistoranteListView.getItems().add(row);
        }
        tipoCucinaRistoranteListView.refresh();
        
        if (cucineArrayList.isEmpty()) {
            tipoCucinaRistoranteListView.getItems().add("Nessuna cucina aggiunta");
        }
    }
    /**
     * Metodo per impostare le recensioni del ristorante nel controller.<br>
     */
    public void setRecensioni() {
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
    }
    /**
     * Metodo per verificare se il ristorante e' stato già aggiunto ai preferiti.<br>
     */
    private void isInPreferiti() {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        if(utenteLoggato != null) {
            ArrayList<String[]> preferitiUtente = utenteLoggato.getPreferiti();
            for (String[] strings : preferitiUtente) {
                
                if(preferitiUtente!=null && strings[16].equals(ristorante[16])) {
                    GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
                    gestoreUtenti.printPreferitiUtente();
                    aggiungiPreferitiButton.setDisable(true);
                    rimuoviPreferitiButton.setDisable(false);
                    aggiungiPreferitiButton.setText("Aggiunto ai preferiti");
                    rimuoviPreferitiButton.setText("Rimuovi dai preferiti");
                    rimuoviPreferitiButton.setVisible(true);
                    break;
                }
                else {
                    rimuoviPreferitiButton.setDisable(true);
                    aggiungiPreferitiButton.setDisable(false);
                    aggiungiPreferitiButton.setText("Aggiungi ai preferiti");
                    rimuoviPreferitiButton.setText("Rimuovi dai preferiti");
                    rimuoviPreferitiButton.setVisible(false);
                }
            }
            if(preferitiUtente.isEmpty()){
                rimuoviPreferitiButton.setDisable(true);
                aggiungiPreferitiButton.setDisable(false);
                aggiungiPreferitiButton.setText("Aggiungi ai preferiti");
                rimuoviPreferitiButton.setText("Rimuovi dai preferiti");
                rimuoviPreferitiButton.setVisible(false);
            }
        }
    }
    /**
     * Metodo FXML per aprire la finestra per rispondere alla recensione.<br>
     * Questo è possibile solo se si è il proprietario del ristorante.<br>
     * @throws IOException
     */
    @FXML
    private void rispondiButtonAction() throws IOException{
        String selectedRecensione = (String) recensioniRistoranteListView.getSelectionModel().getSelectedItem();
        String[] partiRecensione = selectedRecensione.split(" - ");
        String titoloSelezionato = partiRecensione[0].replace("Titolo: ", "").trim();
        String UtenteSelezionato = partiRecensione[1].replace("By: ", "").trim();
        String DataSelezionata = partiRecensione[3].trim();
        String OraSelezionata = partiRecensione[4].trim();

        Recensione recensioneDaVisualizzare = null;
        for(Recensione rec : recensioni) {
            if(rec.utenteRecensione.equals(UtenteSelezionato)&& rec.getData().equals(DataSelezionata) && rec.getOra().equals(OraSelezionata)) {
                recensioneDaVisualizzare = rec;
                break;
            }
        }
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
    /**
     * Metodo FXML per aprire la finestra per visualizzare la recensione.<br>
     * Viene visualizzata la recensione con risposta se presente, altrimenti viene visualizzata la recensione senza risposta.<br>
     * @throws IOException
     */
    @FXML
    private void visualizzaRecensione() throws IOException {
        String selectedRecensione = (String) recensioniRistoranteListView.getSelectionModel().getSelectedItem();
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
                FXMLLoader loader = new FXMLLoader(App.class.getResource("VisualizzaRecensioneSenzaRisposta.fxml"));
                Parent root = loader.load();

                ControllerVisualizzaRecensioneSenzaRisposta controller = loader.getController();
                controller.setRecensione(recensioneDaVisualizzare);

                Stage stage = new Stage();
                stage.setTitle("Visualizza Recensione");
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
     * Metodo FXML per rimuovere una cucina dal ristorante quando avviene la modifica.<br>
     * Rimuove anche la cucina dalla lista di cucine.<br>
     * Chiama il metodo fillCucineListView per aggiornare la lista di cucine.<br>
     * @throws IOException
     */
    @FXML 
    private void rimuoviCucina() throws IOException {
        String selected=tipoCucinaRistoranteListView.getSelectionModel().getSelectedItem().toString();
        cucineArrayList.remove(selected);
        fillCucineListView();
        for (javafx.scene.control.CheckMenuItem checkMenuItem : checkMenuItemsList) {
            if(checkMenuItem.getText().equals(selected)){
                checkMenuItem.setSelected(false);
                break;
            }
        }
    }

    /**
     * Metodo per aggiornare i campi del ristorante.<br>
     * Viene utilizzato per evitare di inizializzare di nuovo i campi del ristorante.<br>
     */
    private void refreshData() {
        setServizi(); 
        setTipiCucina();
        errorFieldcampiVuotiLabel.setVisible(false);
        errorStatoCittaLabel.setVisible(false);
        nomeRistoranteField.setText(ristorante[0]);
        indirizzoRistoranteField.setText(ristorante[1]);
        telefonoRistoranteField.setText(ristorante[8]); 
        statoRistoranteField.setText(ristorante[2]);
        sitoWebRistoranteField.setText(ristorante[9]);
        cittaRistoranteField.setText(ristorante[3]);
        awardField.setText(ristorante[10]);
        descrizioneRistoranteTextArea.setText(ristorante[12]);
        fillServiziListView();
        cucineArrayList.clear();
        for (String cucina : tipiCucina) {
            if (!cucina.trim().isEmpty()) {
                cucineArrayList.add(cucina.trim());
            }
        }
        aggiornaSelezioneCucine();
        tipoCucinaRistoranteListView.getItems().clear();
        tipoCucinaRistoranteListView.getItems().addAll(cucineArrayList);
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
        fillListView(recensioni);
        prz = ristorante[4];
        setPrezzo();
        setRating();
        prenotazioniCheckBox.setSelected(ristorante[15].equals("1"));
        consegnaCheckBox.setSelected(ristorante[14].equals("1"));
    }
    /**
     * Metodo FXML per salvare i dati del ristorante quando avviene la modifica.<br>
     * <ul>
     * <li>Chiama il metodo modificaCucina per aggiornare la cucina del ristorante.</li>
     * <li>Chiama il metodo modificaText per aggiornare il testo del ristorante.</li>
     * <li>Chiama il metodo modificaChekbox per aggiornare le checkbox del ristorante.</li>
     * <li>Chiama il metodo refreshData per aggiornare i dati del ristorante.</li>
     * </ul>
     * @throws IOException
     */
    @FXML 
    private void salvaData() throws IOException {
        String idRistorante = this.ristorante[16];
        int idRow = gestoreDataset.getId(idRistorante);
        errorFieldcampiVuotiLabel.setVisible(false);
        errorStatoCittaLabel.setVisible(false);
        
        if (modificaText() && modificaCucina()) {
            int validazione = gestoreDataset.validaStatoCitta(cittaRistoranteField.getText(), statoRistoranteField.getText());
            if (validazione == 1) {
                String statoEsistente = gestoreDataset.findStatoByCitta(cittaRistoranteField.getText());
                errorStatoCittaLabel.setText("Errore: " + cittaRistoranteField.getText() + " esiste già in " + statoEsistente);
                errorStatoCittaLabel.setVisible(true);
                return;
            }
            
            modificaChekbox();
            gestoreDataset.setRiga(idRow, ristorante);
            refreshData();
            verificaECreaStatoCitta(cittaRistoranteField.getText(), statoRistoranteField.getText());
            System.out.println("Dati salvati con successo!");
        } else {
            errorFieldcampiVuotiLabel.setVisible(true);
        }
    }
    /**
     * Metodo per verificare e creare il stato e la citta' del ristorante.<br>
     * @param citta citta' del ristorante
     * @param stato stato del ristorante
     */
    private void verificaECreaStatoCitta(String citta, String stato) {
        if (citta.isEmpty()) {
            return;
        }
        String statoTrovato = gestoreDataset.findStatoByCitta(citta);
        if (statoTrovato == null || statoTrovato.isEmpty()) {
            gestoreDataset.addNewStato(stato);
            gestoreDataset.addNewCitta(stato, citta);
        }
    }

    /**
     * Metodo per aggiornare la cucina del ristorante.<br>
     * @return boolean true se almeno una cucina e' stata selezionata, false altrimenti
     */
    private boolean modificaCucina() {
        String cucina="";
        boolean checkFirst=true;
        for(String string : cucineArrayList){
            if(checkFirst){
                cucina=string;
                checkFirst=false;
            } else {
                cucina=cucina+","+string;
            }
        }
        if (cucina.isEmpty()) {
            return false;
        }
        ristorante[5] = cucina;
        return true;
    }
    /**
     * Metodo per aggiornare i diversi parametri del ristorante.<br>
     * Tra cui nome, indirizzo, telefono, stato, sito web, citta, award, descrizione.<br>
     * @return boolean true se tutti i campi sono stati inseriti, false altrimenti
     */
    private boolean modificaText() {
        if (nomeRistoranteField.getText().isEmpty() || indirizzoRistoranteField.getText().isEmpty() || telefonoRistoranteField.getText().isEmpty()
            ||statoRistoranteField.getText().isEmpty()||sitoWebRistoranteField.getText().isEmpty()
            ||cittaRistoranteField.getText().isEmpty()||awardField.getText().isEmpty()||descrizioneRistoranteTextArea.getText().isEmpty()) {
            
            return false;
        }
        ristorante[0] = nomeRistoranteField.getText();
        ristorante[1] = indirizzoRistoranteField.getText();
        ristorante[8] = telefonoRistoranteField.getText();
        ristorante[2] = statoRistoranteField.getText();
        ristorante[9] = sitoWebRistoranteField.getText();
        ristorante[3] = cittaRistoranteField.getText();
        ristorante[10] = awardField.getText();
        ristorante[12] = descrizioneRistoranteTextArea.getText();
        
        if (getPrz() == null || getPrz().isEmpty() ||serviziAdd == null || serviziAdd.isEmpty()) {
            return false;
        }
        ristorante[4] = getPrz();
        ristorante[11] = serviziAdd;
        return true;
    }

    /**
     * Metodo per aggiornare le checkbox del ristorante.<br>
     * Tra cui consegna e prenotazioni.
     */
    private void modificaChekbox() {
        if (consegnaCheckBox.isSelected()) {
            ristorante[14] = "1";
        } else {
            ristorante[14] = "0";
        }
        
        if (prenotazioniCheckBox.isSelected()) {
            ristorante[15] = "1";
        } else {
            ristorante[15] = "0";
        }
    }

    /**
     * Metodo FXML per impostare il prezzo del ristorante a un valore.<br>
     * Chiama il metodo setFalsePrezzo per impostare tutte le checkbox di prezzo a false.<br>
     * Chiama il metodo setPrz per impostare il prezzo del ristorante.<br>
     */
    @FXML
    private void handlePrezzo1() {
        if (prezzo1CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo1CheckMenuItem.setSelected(true);
            setPrz("€");
        }
    }

    /**
     * Metodo FXML per impostare il prezzo del ristorante a due di valore.<br>
     * Chiama il metodo setFalsePrezzo per impostare tutte le checkbox di prezzo a false.<br>
     * Chiama il metodo setPrz per impostare il prezzo del ristorante.<br>
     */
    @FXML
    private void handlePrezzo2() {
        if (prezzo2CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo2CheckMenuItem.setSelected(true);
            setPrz("€€");
        }
    }

    /**
     * Metodo FXML per impostare il prezzo del ristorante a tre di valore.<br>
     * Chiama il metodo setFalsePrezzo per impostare tutte le checkbox di prezzo a false.<br>
     * Chiama il metodo setPrz per impostare il prezzo del ristorante.<br>
     */
    @FXML
    private void handlePrezzo3() {
        if (prezzo3CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo3CheckMenuItem.setSelected(true);
            setPrz("€€€");
        }
    }

    /**
     * Metodo FXML per impostare il prezzo del ristorante a quattro di valore.<br>
     * Chiama il metodo setFalsePrezzo per impostare tutte le checkbox di prezzo a false.<br>
     * Chiama il metodo setPrz per impostare il prezzo del ristorante.<br>
     */
    @FXML
    private void handlePrezzo4() {
        if (prezzo4CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo4CheckMenuItem.setSelected(true);
            setPrz("€€€€");
        }
    }

    /**
     * Metodo FXML per aggiungere un servizio al ristorante.<br>
     * Chiama il metodo addServizioByString per aggiungere il servizio al ristorante.<br>
     * Chiama il metodo setServizi per impostare il servizio del ristorante.<br>
     * Chiama il metodo fillServiziListView per riempire la ListView con i servizi del ristorante.<br>
     * @throws IOException
     */
    @FXML 
    private void addServizio() throws IOException {
        String text = serviziTextField.getText().trim();
        if (!text.isEmpty()) {
            serviziTextField.setText("");
            addServizioByString(text);
            ristorante[11] = serviziAdd;
            setServizi(); 
            fillServiziListView();
        }
    }
    /**
     * Metodo per aggiungere un servizio al ristorante controllando se il servizio e' gia' presente.<br>
     * @param text il servizio da aggiungere
     */
    private void addServizioByString(String text) {
        if (serviziAdd.contains(text)) {
            serviziAdd = serviziAdd.replace(text, "");
            if (serviziAdd.contains(",,")) {
                serviziAdd = serviziAdd.replace(",,",",");
            }
            if (serviziAdd.charAt(0) == ',') {
                serviziAdd = serviziAdd.replaceFirst(",","");
            }
            if (!serviziAdd.isEmpty() && serviziAdd.charAt(serviziAdd.length() - 1) == ',') {
                serviziAdd = serviziAdd.substring(0, serviziAdd.length() - 1);
            }
        } else {
            if (serviziAdd.isEmpty()) {
                serviziAdd = text;
            } else {
                serviziAdd = serviziAdd + "," + text;
            }
        }
    }

    /**
     * Metodo FXML per rimuovere un servizio dal ristorante verificando se il servizio e' presente.<br>
     * Chiama il metodo setServizi per impostare il servizio del ristorante.<br>
     * Chiama il metodo fillServiziListView per riempire la ListView con i servizi del ristorante.<br>
     * @throws IOException
     */
    @FXML 
    private void rimuoviServizio() throws IOException {
        int selectedIdx = serviziRistoranteListView.getSelectionModel().getSelectedIndex();
        
        if (selectedIdx != -1) {
            Object[] currentItems = serviziRistoranteListView.getItems().toArray();
            serviziAdd = ""; 
            for (int i = 0; i < currentItems.length; i++) {
                if (i != selectedIdx && !currentItems[i].toString().equals("Non è presente alcun servizio")) {
                    if (serviziAdd.isEmpty()) {
                        serviziAdd = currentItems[i].toString();
                    } else {
                        serviziAdd += "," + currentItems[i].toString();
                    }
                }
            }
            ristorante[11] = serviziAdd;
            
            setServizi();
            fillServiziListView();
        }
    }

    /**
     * Metodo FXML per aggiungere il ristorante ai preferiti.<br>
     * @throws IOException
     */
    @FXML
    private void toPreferiti() throws IOException {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        if(utenteLoggato != null) {
            utenteLoggato.addPreferito(ristorante);
            aggiungiPreferitiButton.setDisable(true);
            rimuoviPreferitiButton.setDisable(false);
            rimuoviPreferitiButton.setVisible(true);
            aggiungiPreferitiButton.setText("Aggiunto ai preferiti");
            rimuoviPreferitiButton.setText("Rimuovi dai preferiti");
        }
    }
    /**
     * Metodo FXML per rimuovere il ristorante dai preferiti.<br>
     * @throws IOException
     */
    @FXML
    private void removePreferiti() throws IOException {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        if(utenteLoggato != null) {
            utenteLoggato.removePreferito(ristorante);
            rimuoviPreferitiButton.setDisable(true);
            aggiungiPreferitiButton.setDisable(false);
            aggiungiPreferitiButton.setText("Aggiungi ai preferiti");
            rimuoviPreferitiButton.setText("Rimosso dai preferiti");
        }
    }

    /**
     * Metodo per riempire la ListView con le recensioni del ristorante.<br>
     * Se non ci sono recensioni, viene mostrato il messaggio "non ci sono recensioni".<br>
     * @param list
     */
    public void fillListView(ArrayList<Recensione> list) {
        recensioniRistoranteListView.getItems().clear();
        
        for (Recensione row : list) {
            if (row != null) {
                String appoggio ="Titolo: "+row.titolo+" - By: "+row.utenteRecensione + " - Voto: ";
                for(int i=0; i<row.stelle; i++) {
                    appoggio += "★";
                }
                appoggio += " - "+row.data+" - "+row.ora+"\n";
                recensioniRistoranteListView.getItems().add(appoggio);
                recensioniRistoranteListView.refresh();
            }
        }
        if (list.isEmpty()){
            recensioniRistoranteListView.getItems().add("non ci sono recensioni.");
            recensioniRistoranteListView.refresh();
        }
    }
    /**
     * Metodo per verificare se l'utente e' loggato.<br>
     * @return true se l'utente e' loggato, false altrimenti
     */
    private boolean isLogged() {
        Gestore gestore = Gestore.getGestore();
        if(gestore.getUtenteLoggato()==null || Modifica) {
            aggiungiRecensioneButton.setDisable(true);
            aggiungiRecensioneButton.setVisible(false);
            return false;
        }
        else {
            aggiungiRecensioneButton.setDisable(false);
            aggiungiRecensioneButton.setVisible(true);
            return true;
        }
    }
    /**
     * Metodo per ottenere il ristorante.<br>
     * @return ristorante con i suoi parametri
     */
    public static String[] getRistorante() {
        return ristorante;
    }

    /**
     * Metodo FXML per tornare alla Home.<br>
     * Se l'utente e' loggato, viene mostrata la HomeLogged, altrimenti la HomeNotLogged.<br>
     * @throws IOException
     */
    @FXML
    private void toHome() throws IOException {
        if(Gestore.getGestore().getUtenteLoggato()==null)
            App.setRoot("HomeNotLogged");
        else
            App.setRoot("HomeLogged");
    }

    /**
     * Metodo FXML per aggiungere una recensione al ristorante.<br>
     * Avviene tramite la finestra Recensisci.<br>
     * Infine chiama il metodo setRating per aggiornare la valutazione del ristorante.<br>
     * @throws IOException
     */
    @FXML
    private void aggiungiRecensione() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Recensisci.fxml"));
        Parent root = loader.load();
        ControllerRecensisci controller = loader.getController();
        controller.setControllerViewRistorante(this);
        Stage stage = new Stage();
        stage.setTitle("Recensisci ristorante");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        setRating();
    }
    /**
     * Metodo per aggiornare la valutazione del ristorante.<br>
     */
    public void setRating() {
        double rating = gestoreDataset.calcStelle(ristorante[13]);
        ratingRistorante.setRating(rating);
    }

    /**
     * Metodo per visualizzare il logo The Knife.<br>
     */
    public void theKnifeImageViewSet() {
        
        String knifePath = System.getProperty("user.dir")
               + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
      java.io.File knifeFile = new java.io.File(knifePath);
      if (knifeFile.exists()) {       
         theKnifeImageView.setImage(
                  new javafx.scene.image.Image(knifeFile.toURI().toString())
         );
         theKnifeImageView.setVisible(true);
      }else{
         knifePath=System.getProperty("user.dir")+ "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
         knifeFile = new java.io.File(knifePath);
         if (knifeFile.exists()) {       
               theKnifeImageView.setImage(
                     new javafx.scene.image.Image(knifeFile.toURI().toString())
               );
               theKnifeImageView.setVisible(true); 
         }
      }
    }
    /**
     * Metodo per verificare se l'utente e' il proprietario del ristorante.<br>
     * Se l'utente e' il proprietario del ristorante, vengono visualizzati i pulsanti di modifica.<br>
     */
    private void isEditable() {
        if(!Modifica) {
            nomeRistoranteField.setEditable(false);
            indirizzoRistoranteField.setEditable(false);
            telefonoRistoranteField.setEditable(false);
            statoRistoranteField.setEditable(false);
            //tipoCucinaRistoranteListView.setDisable(true);
            sitoWebRistoranteField.setEditable(false);
            cittaRistoranteField.setEditable(false);
            awardField.setEditable(false);
            //serviziRistoranteListView.setDisable(true);
            descrizioneRistoranteTextArea.setEditable(false);
            prenotazioniCheckBox.setDisable(true);
            consegnaCheckBox.setDisable(true);
            //prezzoMenuButton.setDisable(true);
            ratingRistorante.setDisable(true);
            prezzo1CheckMenuItem.setDisable(true);
            prezzo2CheckMenuItem.setDisable(true);
            prezzo3CheckMenuItem.setDisable(true);
            prezzo4CheckMenuItem.setDisable(true);
            modificaServiziButton.setDisable(true);
            modificaServiziButton.setVisible(false);
            salvaButton.setDisable(true);
            salvaButton.setVisible(false);
            rimuoviServiziButton.setDisable(true);
            rimuoviServiziButton.setVisible(false);
            serviziTextField.setVisible(false);
            rimuoviCucineButton.setDisable(true);
            rimuoviCucineButton.setVisible(false);
            cucineFilterComboBox.setDisable(true);
            cucineFilterComboBox.setVisible(false);
        }
        else {
            aggiungiRecensioneButton.setDisable(true);
            aggiungiRecensioneButton.setVisible(false);
            ratingRistorante.setDisable(true);
            aggiungiRecensioneButton.setDisable(true);
            aggiungiRecensioneButton.setVisible(false);
            salvaButton.setDisable(false);
            salvaButton.setVisible(true);

        }
    }
    /**
     * Metodo per disabilitare tutti i pulsanti di prezzo.<br>
     */
    private void setFalsePrezzo() {
        prezzo1CheckMenuItem.setSelected(false);
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    /**
     * Metodo per impostare il valore del prezzo del ristorante.<br>
     * @param value valore del prezzo del ristorante
     */
    private void setPrz(String value) {
        prz = value;
    }
    /**
     * Metodo per ottenere il prezzo del ristorante.<br>
     * @return prezzo del ristorante
     */
    private String getPrz() {
        return prz;
    }
    /**
     * Metodo per impostare il prezzo del ristorante.<br>
     * Chiama il metodo setPrezzo per impostare il valore del prezzo del ristorante.<br>
     */
    private void setPrezzo() {
        switch(ristorante[4].length()) {
            case 1:
                setFalsePrezzo();
                prezzo1CheckMenuItem.setSelected(true);
                setPrz("€");
                break;
            case 2:
                setFalsePrezzo();
                prezzo2CheckMenuItem.setSelected(true);
                setPrz("€€");
                break;
            case 3:
                setFalsePrezzo();
                prezzo3CheckMenuItem.setSelected(true);
                setPrz("€€€");
                break;
            case 4:
                setFalsePrezzo();
                prezzo4CheckMenuItem.setSelected(true);
                setPrz("€€€€");
                break;
            default:
                setPrz("");
                break;
        }
    }
    /**
     * Metodo per impostare i tipi di cucina del ristorante.<br>
     */
    private void setTipiCucina() {
        tipiCucina=ristorante[5].split(",");
    }
    /**
     * Metodo per impostare i servizi del ristorante.<br>
     */
    public void setServizi() {
        servizi=ristorante[11].split(",");
        serviziAdd = ristorante[11];
    }

}
