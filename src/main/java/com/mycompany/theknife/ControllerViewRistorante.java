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

public class ControllerViewRistorante {
    @FXML
    private javafx.scene.control.TextField nomeRistoranteField;
    @FXML
    private TextField indirizzoRistoranteField;
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
    private Button modificaTipiCucinaButton;
    @FXML
    private Button modificaServiziButton;
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
    private Button rispondiButton;

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
    public ControllerViewRistorante() {
        
    }
    private ControllerViewRistorante(String[] ristoranteInfo, boolean modifica) {
        
        ristorante = ristoranteInfo;
        this.Modifica = modifica;
    }
    public static ControllerViewRistorante getInstance(String[] ristoranteInfo, boolean modifica) {
        if(instance == null)
            instance =new ControllerViewRistorante(ristoranteInfo, modifica);
        else {
            ristorante = ristoranteInfo;
            Modifica = modifica;
        }
        return instance;
    }
    public void initialize() {
        
        gestoreRecensioni= GestoreRecensioni.getGestoreRecensioni();
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
        gestoreDataset = GestoreDataset.getGestoreDataset();
        setServizi(); 
        setTipiCucina();
        nomeRistoranteField.setText(ristorante[0]);
        indirizzoRistoranteField.setText(ristorante[1]);
        telefonoRistoranteField.setText(ristorante[8]); 
        statoRistoranteField.setText(ristorante[2]);
        tipoCucinaRistoranteListView.getItems().clear();
        tipoCucinaRistoranteListView.getItems().addAll(tipiCucina);
        sitoWebRistoranteField.setText(ristorante[9]);
        cittaRistoranteField.setText(ristorante[3]);
        awardField.setText(ristorante[10]);
        serviziRistoranteListView.getItems().clear();
        serviziRistoranteListView.getItems().addAll(servizi);
        descrizioneRistoranteTextArea.setText(ristorante[12]);
        fillListView(recensioni);
        prz = ristorante[4];
        ratingRistorante.setRating(Double.parseDouble(ristorante[13]));
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
        }
        else {
            aggiungiPreferitiButton.setDisable(true);
            rimuoviPreferitiButton.setDisable(true);
            aggiungiPreferitiButton.setVisible(false);
            rimuoviPreferitiButton.setVisible(false);
            rispondiButton.setDisable(true);
            rispondiButton.setVisible(false);
        }
    }
    
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
    public void setRecensioni() {
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
    }
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
    @FXML 
    private void salvaData() throws IOException {
        String idRistorante = this.ristorante[16];
        int idRow = gestoreDataset.getId(idRistorante);
        modificaText();
        modificaChekbox();
        gestoreDataset.setRiga(idRow, ristorante);
        initialize();
    }

    private void modificaText() {

        ristorante[0] = nomeRistoranteField.getText();
        ristorante[1] = indirizzoRistoranteField.getText();
        ristorante[8] = telefonoRistoranteField.getText();
        ristorante[2] = statoRistoranteField.getText();
        ristorante[9] = sitoWebRistoranteField.getText();
        ristorante[3] = cittaRistoranteField.getText();
        ristorante[10] = awardField.getText();
        ristorante[12] = descrizioneRistoranteTextArea.getText();
        
        if (prz != "" && !prz.isEmpty()) {
            ristorante[4] = getPrz();
        }

        ristorante[11] = serviziAdd;
    }

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

    @FXML
    private void handlePrezzo1() {
        if (prezzo1CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo1CheckMenuItem.setSelected(true);
            setPrz("€");
        }
    }

    @FXML
    private void handlePrezzo2() {
        if (prezzo2CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo2CheckMenuItem.setSelected(true);
            setPrz("€€");
        }
    }

    @FXML
    private void handlePrezzo3() {
        if (prezzo3CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo3CheckMenuItem.setSelected(true);
            setPrz("€€€");
        }
    }

    @FXML
    private void handlePrezzo4() {
        if (prezzo4CheckMenuItem.isSelected()) {
            setFalsePrezzo();
            prezzo4CheckMenuItem.setSelected(true);
            setPrz("€€€€");
        }
    }

    @FXML 
    private void addServizio() throws IOException {
        String text = serviziTextField.getText();
        serviziTextField.setText("");
        
        //serviziRistoranteListView.getItems().addAll(servizi);
        serviziRistoranteListView.getItems().add(text);
        addServizioByString(text);
    }
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

    @FXML 
    private void rimuoviServizio() throws IOException {
        int selectedIdx = serviziRistoranteListView.getSelectionModel().getSelectedIndex();
        
        if (selectedIdx != -1) {
            serviziRistoranteListView.getItems().remove(selectedIdx);
        }
        Object[] currentItems = serviziRistoranteListView.getItems().toArray();
        serviziAdd = ""; 
        
        for (int i = 0; i < currentItems.length; i++) {
            if (i == 0) {
                serviziAdd = currentItems[i].toString();
            } else {
                serviziAdd += "," + currentItems[i].toString();
            }
        }

    }

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
    public static String[] getRistorante() {
        return ristorante;
    }

    @FXML
    private void toHome() throws IOException {
        if(Gestore.getGestore().getUtenteLoggato()==null)
            App.setRoot("HomeNotLogged");
        else
            App.setRoot("HomeLogged");
    }

    @FXML
    private void aggiungiRecensione() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Recensisci.fxml"));
        Parent root = loader.load();
        ControllerRecensisci controller = loader.getController();
        controller.setControllerViewRistorante(this);
        Stage stage = new Stage();
        stage.setTitle("Recensisci ristorante");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // se vuoi bloccare la finestra principale
        stage.show(); // o showAndWait() se ti serve aspettare la chiusura
    }

    public void theKnifeImageViewSet() {
        
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            theKnifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
            theKnifeImageView.setVisible(true);
        }
    }
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
            modificaTipiCucinaButton.setDisable(true);
            modificaServiziButton.setDisable(true);
            modificaServiziButton.setVisible(false);
            modificaTipiCucinaButton.setVisible(false);
            salvaButton.setDisable(true);
            salvaButton.setVisible(false);
            rimuoviServiziButton.setDisable(true);
            rimuoviServiziButton.setVisible(false);
            serviziTextField.setVisible(false);
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
    private void setFalsePrezzo() {
        prezzo1CheckMenuItem.setSelected(false);
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    private void setPrz(String value) {
        prz = value;
    }
    private String getPrz() {
        return prz;
    }
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
    private void setTipiCucina() {
        tipiCucina=ristorante[5].split(",");
    }
    public void setServizi() {
        servizi=ristorante[11].split(",");
        serviziAdd = ristorante[11];
    }

}
