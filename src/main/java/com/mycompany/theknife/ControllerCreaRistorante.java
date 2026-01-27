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

/**
 * @author TheKnifeTeam
 * 
 * ControllerCreaRistorante rappresenta il controller per la scena CreaRistorante.<br>
 * Si occupa di gestire l'interfaccia di creazione di un nuovo ristorante da parte di un ristoratore.<br>
 * 
 * @version 1.0
 */
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
    private TextField longitudineRistoranteField;
    @FXML
    private TextField latitudineRistoranteField;
    @FXML
    private TextField statoRistoranteField;
    @FXML
    private TextField awardField;
    @FXML
    private TextField servizioField;
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
    private Button creaRistoranteButton;
    @FXML
    private javafx.scene.control.MenuButton cucineFilterComboBox;
    @FXML 
    private javafx.scene.control.CheckMenuItem tutteItem;
    @FXML
    private javafx.scene.control.Label errorFieldServiziVuotoLabel;
    @FXML
    private javafx.scene.control.Label errorFieldcampiVuotiLabel;
    ArrayList<String> cucineArrayList;
    ArrayList<String> serviziArrayList;
    ArrayList<javafx.scene.control.CheckMenuItem> checkMenuItemsList;
    private Utente utenteLoggato;
    /**
     * Costruttore di ControllerCreaRistorante.<br>
     */
    public ControllerCreaRistorante(){
    }

    /**
     * Metodo che inizializza la scena con i campi vuoti.<br>
     * Impostando i menu di cucine e servizi e il logo.<br>
     */
    public void initialize() {
        cucineArrayList=new ArrayList<String>();
        serviziArrayList=new ArrayList<String>();
        checkMenuItemsList=new ArrayList<javafx.scene.control.CheckMenuItem>();
        popolaMenuCucineConRadio();
        errorFieldServiziVuotoLabel.setVisible(false);
        errorFieldcampiVuotiLabel.setVisible(false);
        theKnifeImageViewSet();
        fillServiziListView();
        fillCucineListView();
        utenteLoggato = Gestore.getGestore().getUtenteLoggato();
    }
    
    /**
     * Metodo FXML per creare un nuovo ristorante.<br>
     * Esegue il controllo che tutti i campi siano compilati e se sono compilati esegue la creazione del nuovo ristorante.<br>
     */
    @FXML
    private void creaRistoranteButtonAction() throws IOException{
        if(!nomeRistoranteField.getText().isEmpty()&&!indirizzoRistoranteField.getText().isEmpty()&&!statoRistoranteField.getText().isEmpty()&&!cittaRistoranteField.getText().isEmpty()
            &&(prezzo1CheckMenuItem.isSelected()||prezzo2CheckMenuItem.isSelected()||prezzo3CheckMenuItem.isSelected()||prezzo4CheckMenuItem.isSelected())&&!cucineArrayList.isEmpty()
            &&!longitudineRistoranteField.getText().isEmpty()&&!latitudineRistoranteField.getText().isEmpty()&&!telefonoRistoranteField.getText().isEmpty()&&!sitoWebRistoranteField.getText().isEmpty()
            &&!awardField.getText().isEmpty()&&!serviziArrayList.isEmpty()&&!descrizioneRistoranteTextArea.getText().isEmpty()){
            GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
            String[] ristorante =new String[17];
            ristorante[0]=nomeRistoranteField.getText();
            ristorante[1]=indirizzoRistoranteField.getText();
            ristorante[2]=statoRistoranteField.getText();
            ristorante[3]=cittaRistoranteField.getText();
            if(prezzo1CheckMenuItem.isSelected())
                ristorante[4]="€";
            if(prezzo2CheckMenuItem.isSelected())
                ristorante[4]="€€";
            if(prezzo3CheckMenuItem.isSelected())
                ristorante[4]="€€€";
            if(prezzo4CheckMenuItem.isSelected())
                ristorante[4]="€€€€";
            String cucina="";
            boolean checkFirst=true;
            for(String string : cucineArrayList){
                if(checkFirst){
                    cucina=cucina+string;
                    checkFirst=false;
                }
                else
                    cucina=cucina+","+string;
            }
            ristorante[5]=cucina;
            ristorante[6]=latitudineRistoranteField.getText();
            ristorante[7]=longitudineRistoranteField.getText();
            ristorante[8]=telefonoRistoranteField.getText();
            ristorante[9]=sitoWebRistoranteField.getText();
            ristorante[10]=awardField.getText();
            String servizi="";
            for(String string : serviziArrayList){
                servizi=servizi+","+string;
            }
            ristorante[11]=servizi;
            ristorante[12]=descrizioneRistoranteTextArea.getText();
            ristorante[13]="0.0";
            if(consegnaCheckBox.isSelected())
                ristorante[14]="1";
            else
                ristorante[14]="0";
            if(prenotazioniCheckBox.isSelected())
                ristorante[15]="1";
            else
                ristorante[15]="0";
            ristorante[16]=gestoreDataset.LastId()+"";
            gestoreDataset.aggiungiRiga(ristorante);
            GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
            gestoreUtenti.addNewPersoneRistoranti(utenteLoggato.getId(),ristorante[16]);
            App.setRoot("ModRistoratore");
        }
        else{
            errorFieldcampiVuotiLabel.setVisible(true);
        }
    }

    /**
     * Metodo per popolare il menu di cucine con radio button.<br>
     * Chiama il metodo checkFilteredList per popolare il menu di cucine con checkbox.<br>
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
     * Metodo per imposare l'immagine del logo.<br>
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
            knifePath=System.getProperty("user.dir")+ "../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
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
     * Metodo per aggiornare la lista di cucine.<br>
     * Chiama il metodo fillCucineListView per aggiornare la lista di cucine.<br>
     * @param checkItem CheckMenuItem della cucina selezionata
     */
    @FXML
    private void checkFilteredList(javafx.scene.control.CheckMenuItem checkItem) {
        if (checkItem.isSelected()) {
            cucineArrayList.add(checkItem.getText());
        }
        else{
            cucineArrayList.remove(checkItem.getText());
        }
        fillCucineListView();
    }

    /**
     * Metodo per aggiornare la lista di cucine.<br>
     * Se la lista di cucine è vuota, aggiunge "Nessuna cucina aggiunta" al menu.<br>
     */
    private void fillCucineListView(){
        tipoCucinaRistoranteListView.getItems().clear();
        boolean checkfirst = true;
        for (String row : cucineArrayList) {
            
            tipoCucinaRistoranteListView.getItems().add(row);
            tipoCucinaRistoranteListView.refresh();
        
        }
        if (cucineArrayList.isEmpty() || (cucineArrayList.size() == 1 && checkfirst == false)) {
            tipoCucinaRistoranteListView.getItems().add("Nessuna cucina aggiunta");
            tipoCucinaRistoranteListView.refresh();
        }
    }
    /**
     * Metodo per eliminare la cucina selezionata.<br>
     * Chiama il metodo fillCucineListView per aggiornare la lista di cunine.<br>
     */
    @FXML
    private void eliminaCucinaButtonAction(){
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
     * Metodo per eliminare il servizio selezionato.<br>
     * Chiama il metodo fillServiziListView per aggiornare la lista di servizi.<br>
     */
    @FXML
    private void eliminaServiziButtonAction(){
        String selected=serviziRistoranteListView.getSelectionModel().getSelectedItem().toString();
        serviziArrayList.remove(selected);
        fillServiziListView();
    }

    /**
     * Metodo per aggiornare la lista di servizi.<br>
     * Se la lista di servizi è vuota, aggiunge "Nessun servizio aggiunto" al menu.<br>
     */
    private void fillServiziListView() {
        serviziRistoranteListView.getItems().clear();
        for (String row : serviziArrayList) {
        
            serviziRistoranteListView.getItems().add(row);
            serviziRistoranteListView.refresh();
        
        }
        if (serviziArrayList.isEmpty()) {
            serviziRistoranteListView.getItems().add("Nessun servizio aggiunto");
            serviziRistoranteListView.refresh();
        }
    }
    /**
     * Metodo per aggiungere un servizio.<br>
     * Se il campo di servizio non è vuoto, aggiunge il servizio alla lista di servizi.<br>
     * Chiama il metodo fillServiziListView per aggiornare la lista di servizi.<br>
     */
    @FXML
    private void AggiungiServiziButtonAction(){
        if(!servizioField.getText().isEmpty()){
            serviziArrayList.add(servizioField.getText());
            errorFieldServiziVuotoLabel.setVisible(false);
            servizioField.setText("");
            fillServiziListView();
        }
        else{
            errorFieldServiziVuotoLabel.setVisible(true);
        }
    }
    /**
     * Metodo per tornare alla schermata Home.<br>
     * @throws IOException
     */
    @FXML 
    private void switchToHome() throws IOException {
        App.setRoot("HomeLogged");
    }
    /**
     * Metodo per impostare il valore del prezzo.<br>
     * Se il prezzo 1 viene selezionato, disattiva i prezzo 2, 3 e 4.<br>
     */
    @FXML
    private void price1Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    /**
     * Metodo per impostare il valore del prezzo.<br>
     * Se il prezzo 2 viene selezionato, disattiva i prezzo 1, 3 e 4.<br>
     */
    @FXML
    private void price2Action(){
        prezzo1CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    /**
     * Metodo per impostare il valore del prezzo.<br>
     * Se il prezzo 3 viene selezionato, disattiva i prezzo 1, 2 e 4.<br>
     */
    @FXML
    private void price3Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo1CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    /**
     * Metodo per impostare il valore del prezzo.<br>
     * Se il prezzo 4 viene selezionato, disattiva i prezzo 1, 2 e 3.<br>
     */
    @FXML
    private void price4Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo1CheckMenuItem.setSelected(false);
    }
}
