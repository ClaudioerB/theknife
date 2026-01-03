package com.mycompany.theknife;

import java.util.ArrayList;

public class GestoreRicerche {
    private GestoreDataset gestoreDataset;
    private static GestoreRicerche gestoreRicerche;


    public GestoreRicerche() {
        this.gestoreDataset = new GestoreDataset();
        this.gestoreRicerche = this;
    }

    public static GestoreRicerche getGestoreRicerche() {
        return gestoreRicerche;
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

    public void trovaRistorantiCitta(String citta) {

        ArrayList<String[]> ristoranti = new ArrayList<>();
        citta = citta.toLowerCase();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            System.out.println(ristorante[0].toLowerCase());

            if (ristorante[3].toLowerCase().equals(citta)) {
                ristoranti.add(ristorante);
                // System.out.println(ristorante[3]);
            }
        }

        // return ristoranti; ArrayList<String[]>
    }

    public ArrayList<String[]> trovaRistorantiNome(String nome) {

        ArrayList<String[]> ristoranti = new ArrayList<>();
        nome = nome.toLowerCase();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            // System.out.println(ristorante[0].toLowerCase());

            if (ristorante[0].toLowerCase().equals(nome)) {
                ristoranti.add(ristorante);
                // System.out.println(ristorante[0]);
            }
        }


        return ristoranti;
    }
    public ArrayList<String[]> trovaRistorantiDelivery(boolean deliveryY,boolean deliveryN, ArrayList<String[]> ristorantiTrovati) {
        

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[11].toLowerCase().equals("yes") && deliveryY) {

                ristorantiTrovati.add(ristorante);
            }
            else{
                if(ristorante[11].toLowerCase().equals("yes") && !deliveryY){
                    ristorantiTrovati.remove(ristorante);
                }
            }
            
            if (ristorante[11].toLowerCase().equals("no") && deliveryN) {

                ristorantiTrovati.add(ristorante);
            }
            else{
                if(ristorante[11].toLowerCase().equals("no") && !deliveryN){
                    ristorantiTrovati.remove(ristorante);
                }

            }

        }

        return ristorantiTrovati;
    }

    public ArrayList<String[]> trovaRistorantiPrenotation(boolean deliveryY,boolean deliveryN, ArrayList<String[]> ristorantiTrovati) {
        

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[12].toLowerCase().equals("yes") && deliveryY) {

                ristorantiTrovati.add(ristorante);
            }
            else{
                if(ristorante[12].toLowerCase().equals("yes") && !deliveryY){
                    ristorantiTrovati.remove(ristorante);
                }
            }
            
            if (ristorante[12].toLowerCase().equals("no") && deliveryN) {

                ristorantiTrovati.add(ristorante);
            }
            else{
                if(ristorante[12].toLowerCase().equals("no") && !deliveryN){
                    ristorantiTrovati.remove(ristorante);
                }

            }

        }

        return ristorantiTrovati;
    }

    public ArrayList<String[]> trovaRistorantiRating(double rating, ArrayList<String[]> ristorantiTrovati) {
        

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (Double.parseDouble(ristorante[10])>=rating) {

                ristorantiTrovati.add(ristorante);
            }
            else{
                ristorantiTrovati.remove(ristorante);
            }

        }

        return ristorantiTrovati;
    }
}
