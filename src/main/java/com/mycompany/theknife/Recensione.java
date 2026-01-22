package com.mycompany.theknife;

public class Recensione {
 
    String utenteRecensione, testoRecensione, data, ora,id,titolo,Risposta;
    double stelle;

    
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
    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public Recensione() {
    }

    public String getUtenteRecensione() {
        return utenteRecensione;
    }

    public void setUtenteRecensione(String utenteRecensione) {
        this.utenteRecensione = utenteRecensione;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecensione() {
        return testoRecensione;
    }

    public void setRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
    }

    public double getStelle() {
        return stelle;
    }

    public void setStelle(double stelle) {
        this.stelle = stelle;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
    public String getRisposta() {
        return Risposta;
    }
    public void setRisposta(String risposta) {
        Risposta = risposta;
    }


}
