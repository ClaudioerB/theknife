package com.mycompany.theknife;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
public class ControllerRecensisci {
    @FXML
    private javafx.scene.control.TextField TitoloField;
    @FXML
    private javafx.scene.control.TextArea TestoArea;
    @FXML
    private Rating ratingRecensione;
    @FXML
    private javafx.scene.control.Button inviaRecensioneButton;
    @FXML
    private ImageView theKnifeImageView;
    @FXML
    private Label erroreLabel;

    private GestoreRecensioni gestoreRecensioni;
    

    public ControllerRecensisci() {
        
    }   
    public void initialize() {
        erroreLabel.setVisible(false);
        theKnifeImageViewSet();
        gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
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
    private void inviaRecensione() throws IOException {
        String titolo = TitoloField.getText();
        String testo = TestoArea.getText();
        double rating = ratingRecensione.getRating();

        if(titolo.isEmpty() || testo.isEmpty() || rating == 0) {
            erroreLabel.setVisible(true);
            erroreLabel.setText("Per favore, compila tutti i campi.");
        } else {
            Gestore gestore = Gestore.getGestore();
            Utente utenteLoggato = gestore.getUtenteLoggato();
            //String id, String utenteRecensione, String testoRecensione, Double stelle, String data, String ora
            Recensione nuovaRecensione = new Recensione(ControllerViewRistorante.getRistorante()[0],utenteLoggato.getUsername(),titolo,   testo, rating, addData(), addTime());
            gestoreRecensioni.aggiungiRecensione(nuovaRecensione);
        }
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
