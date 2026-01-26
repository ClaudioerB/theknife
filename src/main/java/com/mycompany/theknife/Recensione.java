package com.mycompany.theknife;

/**
 * @author TheKnifeTeam
 * 
 * Recensioni è la classe che definisce una recensione e i suoi parametri.<br>
 * 
 * Le funzionalità includono impostare i parametri dellla recensione, ottenere i suoi parametri e impostare eventualmente una risposta ad essa.<br>
 * Questa classe inizializza il dataset delle recensioni con il suo autore.<br>
 * <br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class Recensione {
 
    String utenteRecensione, testoRecensione, data, ora,id,titolo,Risposta;
    double stelle;

    /**
     * Costruttore della classe Recensione con i parametri richiesti.
     * @param id Id della recensione
     * @param utenteRecensione Utente autore della recensione
     * @param titolo Titolo della recensione
     * @param testoRecensione Testo della recensione
     * @param stelle Valutazione della recensione
     * @param data Data della recensione
     * @param ora Ora della recensione
     */
    public Recensione(String id, String utenteRecensione, String titolo, String testoRecensione,  Double stelle, String data, String ora) {
        this.utenteRecensione = utenteRecensione;
        this.id = id;
        this.testoRecensione = testoRecensione;
        this.stelle = stelle;
        this.data = data;
        this.ora = ora;
        this.titolo=titolo;
        Risposta=" ";
    }
    /**
     * Costruttore della classe Recensione senza parametri.<br>
     */
    public Recensione() {}
    /**
     * Restituisce il titolo della recensione.
     * @return il titolo della recensione.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo della recensione.
     * @param titolo il nuovo titolo da assegnare.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce il nome dell'utente autore della recensione.
     * @return l'identificativo dell'utente.
     */
    public String getUtenteRecensione() {
        return utenteRecensione;
    }

    /**
     * Imposta il nome dell'utente autore della recensione.
     * @param utenteRecensione il nome dell'utente della recensione.
     */
    public void setUtenteRecensione(String utenteRecensione) {
        this.utenteRecensione = utenteRecensione;
    }

    /**
     * Restituisce l'ID associato alla recensione.
     * @return l'ID della recensione.
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'ID della recensione.
     * @param id il nuovo ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Restituisce il contenuto testuale della recensione.
     * @return il testo della recensione.
     */
    public String getRecensione() {
        return testoRecensione;
    }

    /**
     * Imposta il testo della recensione.
     * @param testoRecensione il corpo del messaggio.
     */
    public void setRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
    }

    /**
     * Restituisce il numero di stelle assegnate.
     * @return valore numerico della valutazione.
     */
    public double getStelle() {
        return stelle;
    }

    /**
     * Imposta il numero di stelle per la valutazione.
     * @param stelle il numero di stelle (es. 1.0 - 5.0).
     */
    public void setStelle(double stelle) {
        this.stelle = stelle;
    }

    /**
     * Restituisce la data della recensione.
     * @return la data in formato stringa.
     */
    public String getData() {
        return data;
    }

    /**
     * Imposta la data della recensione.
     * @param data la stringa rappresentante la data.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Restituisce l'ora della recensione.
     * @return l'orario in formato stringa.
     */
    public String getOra() {
        return ora;
    }

    /**
     * Imposta l'ora della recensione.
     * @param ora la stringa rappresentante l'ora.
     */
    public void setOra(String ora) {
        this.ora = ora;
    }

    /**
     * Restituisce la risposta data alla recensione.
     * @return il testo della risposta.
     */
    public String getRisposta() {
        return Risposta;
    }

    /**
     * Imposta la risposta alla recensione.
     * @param risposta il testo della risposta del proprietario.
     */
    public void setRisposta(String risposta) {
        Risposta = risposta;
    }
}
