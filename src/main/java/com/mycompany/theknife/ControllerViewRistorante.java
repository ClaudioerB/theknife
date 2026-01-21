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
    private Button aggiungiRecensioneButton;
    @FXML
    private Button aggiungiPreferitiButton;
    @FXML
    private Button rimuoviPreferitiButton;
    
    private static String[] ristorante;
    private ArrayList<Recensione> recensioni;
    private String[] servizi;
    private String[] tipiCucina;
    private GestoreRecensioni gestoreRecensioni;
    private static boolean Modifica;
    private static ControllerViewRistorante instance = null;
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
        setServizi(); 
        setTipiCucina();
        nomeRistoranteField.setText(ristorante[0]);
        indirizzoRistoranteField.setText(ristorante[1]);
        telefonoRistoranteField.setText(ristorante[8]); 
        statoRistoranteField.setText(ristorante[2]);
        tipoCucinaRistoranteListView.getItems().addAll(tipiCucina);
        sitoWebRistoranteField.setText(ristorante[9]);
        cittaRistoranteField.setText(ristorante[3]);
        awardField.setText(ristorante[10]);
        serviziRistoranteListView.getItems().addAll(servizi);
        descrizioneRistoranteTextArea.setText(ristorante[12]);
        fillListView(recensioni);
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
        if(isLogged())
            isInPreferiti();
        else {
            aggiungiPreferitiButton.setDisable(true);
            rimuoviPreferitiButton.setDisable(true);
            aggiungiPreferitiButton.setVisible(false);
            rimuoviPreferitiButton.setVisible(false);
        }
    }
    private void isInPreferiti() {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        if(utenteLoggato != null) {
            ArrayList<String[]> preferitiUtente = utenteLoggato.getPreferiti();
            if(preferitiUtente!=null && preferitiUtente.contains(ristorante)) {
                aggiungiPreferitiButton.setDisable(true);
                rimuoviPreferitiButton.setDisable(false);
                aggiungiPreferitiButton.setText("Aggiunto ai preferiti");
                rimuoviPreferitiButton.setText("Rimmuovi dai preferiti");
                
            }
            else {
                rimuoviPreferitiButton.setDisable(true);
                aggiungiPreferitiButton.setDisable(false);
                aggiungiPreferitiButton.setText("Aggiungi ai preferiti");
                rimuoviPreferitiButton.setText("Rimuovi dai preferiti");
                rimuoviPreferitiButton.setVisible(false);
            }
        }
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
            else {
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
        if(gestore.getUtenteLoggato()==null) {
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
        }
        else {
            aggiungiRecensioneButton.setDisable(true);
            aggiungiRecensioneButton.setVisible(false);
        }
    }
    private void setPrezzo() {
        switch(ristorante[4].length()) {
            case 1:
                prezzo1CheckMenuItem.setSelected(true);
                break;
            case 2:
                prezzo2CheckMenuItem.setSelected(true);
                break;
            case 3:
                prezzo3CheckMenuItem.setSelected(true);
                break;
            case 4:
                prezzo4CheckMenuItem.setSelected(true);
                break;
            default:
                break;
        }
    }
    private void setTipiCucina() {
        tipiCucina=ristorante[5].split(",");
    }
    public void setServizi() {
        servizi=ristorante[11].split(",");
        
    }

}
