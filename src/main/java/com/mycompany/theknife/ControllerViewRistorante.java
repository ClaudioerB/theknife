package com.mycompany.theknife;

import java.util.ArrayList;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerViewRistorante {
    @FXML
    private javafx.scene.control.TextField nomeRistoranteField;
    @FXML
    private TextField indirizzoRistoranteField;
    @FXML
    private TextField telefonoRistoranteField;
    @FXML
    private ListView tipoCucinaRistoranteField;
    @FXML
    private TextField sitoWebRistoranteField;
    @FXML
    private TextField cittaRistoranteField;
    @FXML
    private TextField statoRistoranteField;
    @FXML
    private TextField awardField;
    @FXML
    private ListView serviziRistoranteField;
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
    
    private static String[] ristorante;
    private ArrayList<Recensione> recensioni;
    private String[] servizi;
    private String[] tipiCucina;
    private GestoreRecensioni gestoreRecensioni;
    private static boolean Modifica;
    private static ControllerViewRistorante instance = null;

    private ControllerViewRistorante(String[] ristoranteInfo, boolean modifica) {
        gestoreRecensioni= GestoreRecensioni.getGestoreRecensioni();
        ristorante = ristoranteInfo;
        this.Modifica = modifica;
    }
    public static void getInstance(String[] ristoranteInfo, boolean modifica) {
        if(instance == null)
            instance =new ControllerViewRistorante(ristoranteInfo, modifica);
        else {
            ristorante = ristoranteInfo;
            Modifica = modifica;
        }
    }
    public void initialize() {
        recensioni = gestoreRecensioni.getRecensioniRistorante(ristorante[16]);
        setServizi(); 
        setTipiCucina();
        nomeRistoranteField.setText(ristorante[0]);
        indirizzoRistoranteField.setText(ristorante[1]);
        telefonoRistoranteField.setText(ristorante[8]); 
        statoRistoranteField.setText(ristorante[2]);
        tipoCucinaRistoranteField.getItems().addAll(tipiCucina);
        sitoWebRistoranteField.setText(ristorante[9]);
        cittaRistoranteField.setText(ristorante[3]);
        awardField.setText(ristorante[10]);
        serviziRistoranteField.getItems().addAll(servizi);
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
    }
    private void isEditable() {
        if(!Modifica) {
            nomeRistoranteField.setEditable(false);
            indirizzoRistoranteField.setEditable(false);
            telefonoRistoranteField.setEditable(false);
            statoRistoranteField.setEditable(false);
            tipoCucinaRistoranteField.setDisable(true);
            sitoWebRistoranteField.setEditable(false);
            cittaRistoranteField.setEditable(false);
            awardField.setEditable(false);
            serviziRistoranteField.setDisable(true);
            descrizioneRistoranteTextArea.setEditable(false);
            prenotazioniCheckBox.setDisable(true);
            consegnaCheckBox.setDisable(true);
            prezzoMenuButton.setDisable(true);
        }
    }
    private void setPrezzo() {
        switch(ristorante[7].length()) {
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
