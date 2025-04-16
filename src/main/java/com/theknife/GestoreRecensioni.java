package com.theknife;

import java.util.ArrayList;

public class GestoreRecensioni {
    ArrayList<Recensione> recensioni;

    public GestoreRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
    
    
}
