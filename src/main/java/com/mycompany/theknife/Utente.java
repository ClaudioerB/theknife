package com.mycompany.theknife;

import java.util.ArrayList;

public class Utente {
    private String nome, cognome, username, email, passwordHash, città, indirizzo, stato, id;
    private boolean isRistoratore;
    private ArrayList<String[]> preferiti;
    private GestoreUtenti gestoreUtenti;

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
        
        this.preferiti = setPreferiti();
        if(this.preferiti==null){
            this.preferiti=new ArrayList<>();
        }
    }
    
    
    private ArrayList<String[]> setPreferiti() {
        gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        return gestoreUtenti.getPreferitiUtente(this.username);
    }
    public ArrayList<String[]> getPreferiti() {
        return this.preferiti;
    }
    public void setPreferiti(ArrayList<String[]> preferiti) {
        this.preferiti = preferiti;
    }
    
    public void addPreferito(String[] ristorante) {
        this.preferiti.add(ristorante);
        gestoreUtenti.aggiungiPreferitoUtente(this.username, ristorante);
    }

    public void removePreferito(String[] ristorante) {
        this.preferiti.remove(ristorante);
    }

    public Utente() {
        this.preferiti = setPreferiti();
        if(this.preferiti==null){
            this.preferiti=new ArrayList<>();
        }
    }

    


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    public boolean isRistoratore() {
        return isRistoratore;
    }
    public void setRistoratore(boolean isRistoratore) {
        this.isRistoratore = isRistoratore;
    }

    
}
