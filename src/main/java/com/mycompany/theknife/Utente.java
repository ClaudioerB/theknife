package com.mycompany.theknife;

import java.util.ArrayList;
/**
 * @author TheKnifeTeam
 * 
 * Utenti è la classe che definisce un utente e i suoi parametri.<br>
 * 
 * Le funzionalità includono impostare i parametri dell'utente, ottenere i suoi parametri e impostare i suoi preferiti.<br>
 * Questa classe inizializza il dataset degli utenti e gestisce anche i dataset dei preferiti degli utenti e anche il dataset delle persone che possiedono un ristorante.<br>
 * <br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class Utente {
    private String nome, cognome, username, email, passwordHash, città, indirizzo, stato, id;
    private boolean isRistoratore;
    private ArrayList<String[]> preferiti;
    private GestoreUtenti gestoreUtenti;

    /**
     * Costruttore di Utente con i suoi parametri da impostare.<br>
     * @param username Lo username dell'utente
     * @param passwordHash La password dell'utente
     * @param email L'email dell'utente
     * @param nome Il nome dell'utente
     * @param cognome Il cognome dell'utente
     * @param stato Lo stato in cui vive ll'utente
     * @param città La città in cui vive ll'utente
     * @param indirizzo L'indirizzo dell'utente
     * @param isRistoratore Indica se l'utente e' un ristoratore
     */
    public Utente(String username, String passwordHash,String email,String nome, String cognome, String stato, String città,String indirizzo, boolean isRistoratore) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.città = città;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.isRistoratore = isRistoratore;
        gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        this.id=gestoreUtenti.nRigheUtentiFile()+"";
        this.preferiti = setPreferiti();
        if(this.preferiti==null){
            this.preferiti=new ArrayList<>();
        }
    }
    
    /**
     * Metodo che restituisce i preferiti dell'utente.<br>
     * @return Un'ArrayList dei preferiti dell'utente
     */
    private ArrayList<String[]> setPreferiti() {
        gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        return gestoreUtenti.getPreferitiUtente(this.username);
    }
    /**
     * Metodo che restituisce i preferiti dell'utente.<br>
     * @return Un'ArrayList dei preferiti dell'utente
     */
    public ArrayList<String[]> getPreferiti() {
        return this.preferiti;
    }
    /**
     * Metodo che imposta i preferiti dell'utente.<br>
     * @param preferiti Un'ArrayList dei preferiti dell'utente
     */
    public void setPreferiti(ArrayList<String[]> preferiti) {
        this.preferiti = preferiti;
    }
    /**
     * Metodo che aggiunge un preferito all'utente.<br>
     * @param ristorante Il ristorante da aggiungere
     */
    public void addPreferito(String[] ristorante) {
        this.preferiti.add(ristorante);
        gestoreUtenti.aggiungiPreferitoUtente(this.username, ristorante);
    }
    /**
     * Metodo che rimuove un preferito all'utente.<br>
     * @param ristorante Il ristorante da rimuovere
     */
    public void removePreferito(String[] ristorante) {
        int i=0;
        for (String[] strings : this.preferiti) {
            if(strings[16].equals(ristorante[16])){
                preferiti.remove(i);
                break;
            }
            i++;   
        }
        gestoreUtenti.rimuoviPreferitoUtente(this.username,ristorante);
    }
    /**
     * Costruttore di Utente.<br>
     * Inizializza l'ArrayList dei preferiti dell'utente.
     */
    public Utente() {
        this.preferiti = setPreferiti();
        if(this.preferiti==null){
            this.preferiti=new ArrayList<>();
        }
    }

    /**
     * Metodo che restituisce l'id dell'utente.<br>
     * @return L'id dell'utente
     */
    public String getId() {
        return id;
    }
    /**
     * Metodo che imposta l'id dell'utente.<br>
     * @param id L'id dell'utente
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Metodo che restituisce il nome dell'utente.<br>
     * @return Il nome dell'utente
     */
    public String getNome() {
        return nome;
    }
    /**
     * Metodo che imposta il nome dell'utente.<br>
     * @param nome Il nome dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Metodo che restituisce il cognome dell'utente.<br>
     * @return Il cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }
    /**
     * Metodo che imposta il cognome dell'utente.<br>
     * @param cognome Il cognome dell'utente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    /**
     * Metodo che restituisce il username dell'utente.<br>
     * @return Lo username dell'utente
     */
    public String getUsername() {
        return username;
    }
    /**
     * Metodo che imposta il username dell'utente.<br>
     * @param username Lo username dell'utente
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Metodo che restituisce l'email dell'utente.<br>
     * @return L'email dell'utente
     */
    public String getEmail() {
        return email;
    }
    /**
     * Metodo che imposta l'email dell'utente.<br>
     * @param email L'email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Metodo che restituisce la password dell'utente.<br>
     * @return La password dell'utente
     */
    public String getPasswordHash() {
        return passwordHash;
    }
    /**
     * Metodo che imposta la password dell'utente.<br>
     * @param passwordHash La password dell'utente
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    /**
     * Metodo che restituisce la città dell'utente.<br>
     * @return La città dell'utente
     */
    public String getCittà() {
        return città;
    }
    /**
     * Metodo che imposta la città dell'utente.<br>
     * @param città La città dell'utente
     */
    public void setCittà(String città) {
        this.città = città;
    }
    /**
     * Metodo che restituisce l'indirizzo dell'utente.<br>     
     * @return L'indirizzo dell'utente
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /**
     * Metodo che imposta l'indirizzo dell'utente.<br>
     * @param indirizzo L'indirizzo dell'utente
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * Metodo che restituisce lo stato dell'utente.<br>
     * @return Lo stato dell'utente
     */
    public String getStato() {
        return stato;
    }
    /**
     * Metodo che imposta lo stato dell'utente.<br>
     * @param stato Lo stato dell'utente
     */
    public void setStato(String stato) {
        this.stato = stato;
    }
    /**
     * Metodo che restituisce se l'utente e' un ristoratore.<br>
     * @return true se l'utente e' un ristoratore, altrimenti false
     */
    public boolean isRistoratore() {
        return isRistoratore;
    }
    /**
     * Metodo che imposta se l'utente e' un ristoratore.<br>
     * @param isRistoratore true se l'utente e' un ristoratore, altrimenti false
     */
    public void setRistoratore(boolean isRistoratore) {
        this.isRistoratore = isRistoratore;
    }

    
}
