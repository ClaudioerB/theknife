package com.mycompany.theknife;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerVisualizzaRecensione {
    @FXML
    private javafx.scene.control.TextField TitoloField;
    @FXML
    private javafx.scene.control.TextArea TestoArea;
    @FXML
    private Rating ratingRecensione;
    @FXML
    private ImageView theKnifeImageView;
    @FXML
    private javafx.scene.control.TextArea RispostaArea;
    @FXML
    private Button salvaButton;

    private Recensione recensioneCorrente;
    
    String[] ristorante;

    public ControllerVisualizzaRecensione() {
        
    }   
    public void initialize() {
        
        theKnifeImageViewSet();
        setDisableFields();
        
        
    }
    private void setRisposta() {
        if(this.recensioneCorrente.Risposta!=null){
            RispostaArea.setText(recensioneCorrente.Risposta);
        }
    }
    private boolean isProprietario() {
        Gestore gestore = Gestore.getGestore();
        Utente utenteLoggato = gestore.getUtenteLoggato();
        GestoreUtenti gestoreUtenti =GestoreUtenti.getGestoreUtenti();
        if(gestoreUtenti.getPersoneRistorantiByIdRistorante(recensioneCorrente.id).equals(utenteLoggato.getId())){
            return true;
        }else{
            return false;
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

   private void setDisableFields() {
        TitoloField.setEditable(false);
        TestoArea.setEditable(false);
        ratingRecensione.setDisable(true);
    }

    public void setRecensione(Recensione recensioneDaVisualizzare) {
        this.recensioneCorrente = recensioneDaVisualizzare;
        TitoloField.setText(recensioneDaVisualizzare.getTitolo());
        TestoArea.setText(recensioneDaVisualizzare.getRecensione());
        ratingRecensione.setRating(recensioneDaVisualizzare.getStelle());
        RispostaArea.setText(recensioneDaVisualizzare.getRisposta());
        setRisposta();
        if(!isProprietario()){
            salvaButton.setDisable(true);
            salvaButton.setVisible(false);
            RispostaArea.setEditable(true);
        }
    }
    @FXML
    private void salvaButtonAction(){
        this.recensioneCorrente.Risposta=RispostaArea.getText();
        
        GestoreRecensioni gestoreRecensioni=GestoreRecensioni.getGestoreRecensioni();
        gestoreRecensioni.modificaRecensione(recensioneCorrente,recensioneCorrente);
        gestoreRecensioni.aggiungiRecensione(recensioneCorrente);
        closeWindow();
    }
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) TitoloField.getScene().getWindow();
        stage.close();
    }
}
