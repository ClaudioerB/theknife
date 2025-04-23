package com.theknife;

public class Utente {
    private String nome, cognome, username, nick, passwordHash, città, indirizzo, numeroCivico, Latitudine,Longitudine;

    public Utente(String città, String cognome, String indirizzo, String nick, String nome, String numeroCivico, String passwordHash, String username) {
        this.città = città;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.nick = nick;
        this.nome = nome;
        this.numeroCivico = numeroCivico;
        this.passwordHash = passwordHash;
        this.username = username;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }
    
}
