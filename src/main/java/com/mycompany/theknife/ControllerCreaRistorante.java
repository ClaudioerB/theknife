package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
public class ControllerCreaRistorante {
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
    private ListView serviziRistoranteListView;
    @FXML
    private CheckBox prenotazioniCheckBox;
    @FXML
    private CheckBox consegnaCheckBox;
    @FXML
    private ImageView theKnifeImageView;
    @FXML
    private Button eliminaTipiCucinaButton;
    @FXML
    private Button aggiungiServiziButton;
    @FXML
    private Button eliminaServiziButton;
    @FXML
    private javafx.scene.control.MenuButton cucineFilterComboBox;
    @FXML 
    private javafx.scene.control.CheckMenuItem tutteItem;

    public ControllerCreaRistorante(){

    }

    public void initialize() {
        popolaMenuCucineConRadio();
    }
   
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
            
            cucineFilterComboBox.getItems().add(checkItem);
        }
    }

    

}
