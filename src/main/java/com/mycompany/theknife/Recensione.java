package com.mycompany.theknife;

public class Recensione {
    //String username, idRistorante, recensione, voto, data, ora, id;
    String utenteRecensione, testoRecensione, data, ora;
    int id, stelle;


    /*
        public Recensione(String username, String idRistorante, String recensione,
        String voto, String data, String ora,
        String id) {
        this.username = username;
        this.idRistorante = idRistorante;
        this.recensione = recensione;
        this.voto = voto;
        this.data = data;
        this.ora = ora;
        this.id = id;
        }
     */

    //io non farei mettere la data e l'ora all'utente quindi le vorrei togliere
    public Recensione(int id, String utenteRecensione, String testoRecensione, int stelle, String data, String ora) {
        this.utenteRecensione = utenteRecensione;
        this.id = id;
        this.testoRecensione = testoRecensione;
        this.stelle = stelle;
        this.data = data;
        this.ora = ora;
    }

    public Recensione() {
    }

    public String getUtenteRecensione() {
        return utenteRecensione;
    }

    public void setUtenteRecensione(String utenteRecensione) {
        this.utenteRecensione = utenteRecensione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecensione() {
        return testoRecensione;
    }

    public void setRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
    }

    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
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

    /*public String addData() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-mm-yyyy"); //giorno-mese-anno -> non so se lasciarlo così per i diversi paesi

        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }
    public String addTime() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");  //ore e minuti

        String formattedTime = myDateObj.format(myFormatObj);

        return formattedTime;
    }*/

}
