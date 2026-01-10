package com.mycompany.theknife;

public class Utente {
    private String nome, cognome, username, email, passwordHash, città, indirizzo, stato;
    private boolean isRistoratore;

    

    public Utente(String username, String passwordHash,String email,String nome, String cognome, String stato, String città,
            String indirizzo, boolean isRistoratore) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.città = città;
        this.indirizzo = indirizzo;
        this.stato = stato;
        this.isRistoratore = isRistoratore;

    }
    
    
    public Utente() {
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
