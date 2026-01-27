package com.mycompany.theknife;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerVisualizzaRecensione rappresenta il controller per la scena di VisualizzaRecensione.<br>
 * Si occupa di gestire l'interfaccia di visualizzazione di una recensione con una risposta del proprietario.<br>
 * Se l'utente è il proprietario della recensione, viene mostrata la possibilità di rispondere alla recensione.<br>
 * 
 * @version 1.0
 */
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

    /**
     * Costruttore di ControllerVisualizzaRecensione<br>
     */
    public ControllerVisualizzaRecensione() {
        
    }   
    /**
     * Metodo di inizializzazione<br>
     * Inizializza il logo e disabilita la modifica ai campi di testo.<br>
     */
    public void initialize() {
        theKnifeImageViewSet();
        setDisableFields();
    }
    /**
     * Metodo per impostare la risposta della recensione.<br>
     */
    private void setRisposta() {
        if(this.recensioneCorrente.Risposta!=null){
            RispostaArea.setText(recensioneCorrente.Risposta);
        }
    }
    /**
     * Metodo per verificare se l'utente loggato è proprietario della recensione.<br>
     * @return
     */
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
    /**
     * Metodo per impostare il logo.<br>
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
            knifePath=System.getProperty("user.dir")+ "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
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
     * Metodo per disabilitare la modifica ai campi di testo.<br>
     */
    private void setDisableFields() {
        TitoloField.setEditable(false);
        TestoArea.setEditable(false);
        ratingRecensione.setDisable(true);
    }

    /**
     * Metodo per impostare la recensione da visualizzare.<br>
     * Chiama il metodo setRisposta per impostare la risposta.<br>
     * Chiama il metodo isProprietario per verificare se l'utente loggato è proprietario della recensione.<br>
     * @param recensioneDaVisualizzare recensione da visualizzare
     */
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
    /**
     * Metodo FXML per salvare la risposta della recensione.<br>
     * Chiama il metodo closeWindow per chiudere la finestra.<br>
     */
    @FXML
    private void salvaButtonAction(){
        this.recensioneCorrente.Risposta=RispostaArea.getText();
        
        GestoreRecensioni gestoreRecensioni=GestoreRecensioni.getGestoreRecensioni();
        gestoreRecensioni.modificaRecensione(recensioneCorrente,recensioneCorrente);
        gestoreRecensioni.aggiungiRecensione(recensioneCorrente);
        closeWindow();
    }
    /**
     * Metodo FXML per chiudere la finestra.<br>
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) TitoloField.getScene().getWindow();
        stage.close();
    }
}
