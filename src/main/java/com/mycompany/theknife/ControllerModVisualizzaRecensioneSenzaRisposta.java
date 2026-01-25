package com.mycompany.theknife;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerModVisualizzaRecensioneSenzaRisposta {
    @FXML
    private javafx.scene.control.TextField TitoloField;
    @FXML
    private javafx.scene.control.TextArea TestoArea;
    @FXML
    private Rating ratingRecensione;
    @FXML
    private javafx.scene.control.Button inviaRecensioneButton;
    @FXML
    private javafx.scene.control.Button indietroButton;
    @FXML
    private javafx.scene.control.Button salvaButton;
    @FXML
    private ImageView theKnifeImageView;
    
    private Recensione recensioneVecchia;
    private Recensione recensioneNuova;
    private ControllerModUser controller;
    private Gestore gestore;
    private GestoreRecensioni gestoreRecensioni;

    public ControllerModVisualizzaRecensioneSenzaRisposta() {}   
    public void setController(ControllerModUser controller) {
        this.controller = controller;
    }
    public void initialize() {
        gestore = Gestore.getGestore();
        gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        theKnifeImageViewSet();
        //setDisableFields();
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
    private void setDisableFields() {
        TitoloField.setEditable(false);
        TestoArea.setEditable(false);
        ratingRecensione.setDisable(true);
    }
    public void setRecensione(Recensione recensioneDaVisualizzare) {
        TitoloField.setText(recensioneDaVisualizzare.getTitolo());
        TestoArea.setText(recensioneDaVisualizzare.getRecensione());
        ratingRecensione.setRating(recensioneDaVisualizzare.getStelle());
    }
    @FXML 
    private void salvaAction() throws IOException {
        recensioneVecchia = controller.getRecensioneVecchia();
        recensioneNuova = recensioneVecchia;
        String titolo = TitoloField.getText();
        String testo = TestoArea.getText();
        double rating = ratingRecensione.getRating();
        String data = addData();
        String time = addTime();

        if(titolo.isEmpty() || testo.isEmpty() || rating == 0) {
            //erroreLabel.setVisible(true);
            //erroreLabel.setText("Per favore, compila tutti i campi.");
        } else {
            recensioneNuova.setTitolo(titolo);
            recensioneNuova.setRecensione(testo);
            recensioneNuova.setStelle(rating);
            recensioneNuova.setData(data);
            recensioneNuova.setOra(time);

            gestoreRecensioni.modificaRecensione(recensioneNuova, recensioneVecchia);
            GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
            if (!controller.getIdRistorante().isEmpty() && controller.getIdRistorante() != null) {
                String idR = controller.getIdRistorante();
                String ratingOld = String.valueOf(recensioneVecchia.getStelle());
                gestoreDataset.changeStelle(String.valueOf(rating), ratingOld, idR);
            } else {
                System.out.println("Non possibile trovare id ristorante per la recensione");
            }
        }
        controller.settingRecensione();
        Stage stage = (Stage) TitoloField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) TitoloField.getScene().getWindow();
        stage.close();
    }
    private String addData() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    private String addTime() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = myDateObj.format(myFormatObj);
        return formattedTime;
    }
}
