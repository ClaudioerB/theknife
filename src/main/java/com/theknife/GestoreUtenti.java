package com.theknife;

import java.util.ArrayList;

public class GestoreUtenti {
    ArrayList<Utente> utenti;

    public GestoreUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    public Utente getUtenteSingolo(int i) {
        return utenti.get(i);
    }

    public void setUtenteSingolo(Utente utente, int i) {
        this.utenti.set(i, utente);
    }
}
