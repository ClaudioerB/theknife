package com.mycompany.theknife;

import java.io.IOException;
import java.lang.classfile.Label;
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
    public ControllerCreaRistorante(){

    }

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
    }
    
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
            ristorante[13]="0";
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
            App.setRoot("ModRistoratore");
        }
        else{
            errorFieldcampiVuotiLabel.setVisible(true);
        }
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
            checkMenuItemsList.add(checkItem);
                checkItem.setOnAction(e -> {
                checkFilteredList(checkItem);
            });
            cucineFilterComboBox.getItems().add(checkItem);
        }
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
    @FXML
    private void checkFilteredList(javafx.scene.control.CheckMenuItem checkItem) {
        cucineArrayList.add(checkItem.getText());
        fillCucineListView();

    }

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
    @FXML
    private void eliminaServiziButtonAction(){
        String selected=serviziRistoranteListView.getSelectionModel().getSelectedItem().toString();
        serviziArrayList.remove(selected);
        fillServiziListView();
            
    }

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
    @FXML 
    private void switchToHome() throws IOException {
        App.setRoot("HomeLogged");
    }
    @FXML
    private void price1Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    @FXML
    private void price2Action(){
        prezzo1CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    @FXML
    private void price3Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo1CheckMenuItem.setSelected(false);
        prezzo4CheckMenuItem.setSelected(false);
    }
    @FXML
    private void price4Action(){
        prezzo2CheckMenuItem.setSelected(false);
        prezzo3CheckMenuItem.setSelected(false);
        prezzo1CheckMenuItem.setSelected(false);
    }
}
