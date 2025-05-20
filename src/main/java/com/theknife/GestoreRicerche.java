package com.theknife;

import java.util.ArrayList;

public class GestoreRicerche {
    GestoreDataset gestoreDataset;
    public GestoreRicerche(GestoreDataset gestoreDataset) {
        this.gestoreDataset = gestoreDataset;
    }

    public ArrayList<String[]> trovaRistorantiVicini(double latitudine, double longitudine, double raggioKm, GestoreDataset dataset) {
        ArrayList<String[]> ristorantiVicini = new ArrayList<>();

        for (String[] ristorante : dataset.getDataSet()) {
            double distanza = calcolaDistanza(latitudine, longitudine,Double.parseDouble(ristorante[6]),Double.parseDouble(ristorante[7]));
            if (distanza <= 10) {
                ristorantiVicini.add(ristorante);
            }
        }

        return ristorantiVicini;
    }

    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della Terra in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    /*
    public ArrayList<String[]> trovaRistorantiDelivery() {
        ArrayList<String[]> ristorantiTrovati = new ArrayList<>();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[0].toLowerCase().contains(nome.toLowerCase())) {
                ristorantiTrovati.add(ristorante);
            }
        }

        return ristorantiTrovati;
    }*/
    // Trova ristoranti per costo
    public ArrayList<String[]> trovaRistorantiCosto(String costo) {
        ArrayList<String[]> ristorantiTrovati = new ArrayList<>();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[4].length()==costo.length()) {
                ristorantiTrovati.add(ristorante);
            }
        }
        return ristorantiTrovati;
    }
}
