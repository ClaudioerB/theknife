package com.theknife;

public class Recensione {
    String username, idRistorante, recensione, voto, data, ora;

    public Recensione(String username, String idRistorante, String recensione, String voto, String data,
            String ora) {
        this.username = username;
        this.idRistorante = idRistorante;
        this.recensione = recensione;
        this.voto = voto;
        this.data = data;
        this.ora = ora;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(String idRistorante) {
        this.idRistorante = idRistorante;
    }

    public String getRecensione() {
        return recensione;
    }

    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
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
    
}
