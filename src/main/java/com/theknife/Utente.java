package com.theknife;

public class Utente {
    private String nome, cognome, username, nick, passwordHash, città, indirizzo, numeroCivico, Latitudine,Longitudine;

    

    public Utente(String nome, String cognome, String username, String nick, String passwordHash, String città,
            String indirizzo, String numeroCivico, String latitudine, String longitudine) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.nick = nick;
        this.passwordHash = passwordHash;
        this.città = città;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        Latitudine = latitudine;
        Longitudine = longitudine;
    }
    
    
    public Utente() {
    }

    

    public String getLatitudine() {
        return Latitudine;
    }


    public void setLatitudine(String latitudine) {
        Latitudine = latitudine;
    }


    public String getLongitudine() {
        return Longitudine;
    }


    public void setLongitudine(String longitudine) {
        Longitudine = longitudine;
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
