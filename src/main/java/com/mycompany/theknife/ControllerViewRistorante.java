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
        recensioniRistoranteListView.getItems().addAll(recensioni);
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
        isLogged();
    }
    private void isLogged() {
        Gestore gestore = Gestore.getGestore();
        if(gestore.getUtenteLoggato()==null) {
            aggiungiRecensioneButton.setDisable(true);
            aggiungiRecensioneButton.setVisible(false);
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
