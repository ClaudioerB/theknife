package com.mycompany.theknife;

import java.util.ArrayList;

/**
 * @author TheKnifeTeam
 * 
 * GestoreRicerche è la classe che gestisce alcune ricerche nel dataset dei ristoranti.<br>
 * 
 * Le funzionalità includono la ricerca con diversi fattori nel dataset dei ristoranti.<br>
 * <br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class GestoreRicerche {
    private GestoreDataset gestoreDataset;
    private static GestoreRicerche gestoreRicerche;

    /**
     * Costruttore della classe GestoreRicerche che inizializza il dataset delle ricerche e il dataset dei ristoranti.<br>
     */
    public GestoreRicerche() {
        this.gestoreDataset = gestoreDataset.getGestoreDataset();
        this.gestoreRicerche = this; 
    }
    /**
     * Metodo che restituisce l'istanza di GestoreRicerche. Se l'istanza non esiste, viene creata una nuova istanza.<br>
     * @return istanza di GestoreRicerche
     */
    public static GestoreRicerche getGestoreRicerche() {
        if(gestoreRicerche == null) {
            gestoreRicerche = new GestoreRicerche();
        }
        return gestoreRicerche;
    }
    /**
     * Metodo che trova i ristoranti vicini a una posizione specifica<br>
     * @param latitudine valore di latitudine
     * @param longitudine valore di longitudine
     * @param raggioKm raggio di ricerca in km
     * @param dataset dataset dei ristoranti
     * @return Arraylist di ristoranti vicini
     */
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
    /**
     * Metodo che calcola la distanza tra due posizioni<br>
     * @param lat1 latitudine della posizione 1
     * @param lon1 longitudine della posizione 1
     * @param lat2 latitudine della posizione 2
     * @param lon2 longitudine della posizione 2
     * @return distanza in km tra le due posizioni
     */
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
    /**
     * Metodo che trova i ristoranti con un costo specifico<br>
     * @param costo costo del ristorante in valore String
     * @param listaDaFiltrare dataset dei ristoranti
     * @return ArrayList di ristoranti con costo specifico
     */
    public ArrayList<String[]> trovaRistorantiCosto(String costo,ArrayList<String[]> listaDaFiltrare) {
        int index = 0;
        for (String[] ristorante : listaDaFiltrare) {
            if (ristorante[4].length()==costo.length()) {
                listaDaFiltrare.remove(index);
            }
            index++;
        }
        return listaDaFiltrare;
    }
    /**
     * Metodo che trova i ristoranti in una citta specifica.<br>
     * @param citta citta in cui cercare i ristoranti
     */
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
    /**
     * Metodo che trova i ristoranti con un id specifico<br>
     * @param id id del ristorante
     * @return ristorante con id specifico
     */
    public String[] trovaRistorantiID(String id) {
        
        id = id.toLowerCase();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            // System.out.println(ristorante[0].toLowerCase());

            if (ristorante[16].toLowerCase().equals(id)) {
                return ristorante;
                // System.out.println(ristorante[0]);
            }
        }
        return null;
    }
    /**
     * Metodo che trova i ristoranti con un nome specifico<br>
     * @param nome nome del ristorante da cercare
     * @return ArrayList di ristoranti con nome specifico
     */
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
    /**
     * Metodo che trova i ristoranti con un delivery specifico<br>
     * @param deliveryY delivery yes 
     * @param deliveryN delivery no
     * @param ristorantiTrovati ArrayList di ristoranti da cui cercare
     * @return ArrayList di ristoranti con delivery specifico
     */
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
    /**
     * Metodo che trova i ristoranti con un prenotation specifico<br>
     * @param deliveryY prenotation yes
     * @param deliveryN prenotation no
     * @param ristorantiTrovati ArrayList di ristoranti da cui cercare
     * @return ArrayList di ristoranti con prenotation specifico
     */
    public ArrayList<String[]> trovaRistorantiPrenotation(boolean deliveryY,boolean deliveryN, ArrayList<String[]> ristorantiTrovati) {
        

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[15].toLowerCase().equals("1") && deliveryY) {

                
            }
            else{
                if(ristorante[15].toLowerCase().equals("1") && !deliveryY){
                    ristorantiTrovati.remove(ristorante);
                }
            }
            
            if (ristorante[15].toLowerCase().equals("0") && deliveryN) {

                
            }
            else{
                if(ristorante[15].toLowerCase().equals("0") && !deliveryN){
                    ristorantiTrovati.remove(ristorante);
                }

            }

        }

        return ristorantiTrovati;
    }
    /**
     * Metodo che trova i ristoranti con un rating specifico<br>
     * @param rating rating del ristorante
     * @param ristorantiTrovati ArrayList di ristoranti da cui cercare
     * @return ArrayList di ristoranti con rating specifico
     */
    public ArrayList<String[]> trovaRistorantiRating(double rating, ArrayList<String[]> ristorantiTrovati) {
        

        for (String[] ristorante : ristorantiTrovati) {
            if(!ristorante[13].equals("Rating"))
                if (Double.parseDouble(ristorante[13])>=rating) {

                }
                else{
                    ristorantiTrovati.remove(ristorante);
                }

        }

        return ristorantiTrovati;
    }
    /**
     * Metodo che trova i ristoranti con un delivery specifico<br>
     * @param deliveryY delivery yes
     * @param deliveryN delivery no
     * @param ristorantiDaFiltrare ArrayList di ristoranti da cui cercare
     * @return ArrayList di ristoranti con delivery specifico
     */
    public ArrayList<String[]> searchingDelivery(boolean deliveryY,boolean deliveryN, ArrayList<String[]> ristorantiDaFiltrare) {
        ArrayList<String[]> ristorantiFiltrati = new ArrayList<>(ristorantiDaFiltrare);
        GestoreDataset gestoreDataset = new GestoreDataset();

        for (String[] ristorante : gestoreDataset.getDataSet()) {
            if (ristorante[14].toLowerCase().equals("yes") && !deliveryY) {
                ristorantiFiltrati.remove(ristorante);
                System.out.println("Ristorante rimosso: " + ristorante[14].toLowerCase());
            }
            
            if (ristorante[14].toLowerCase().equals("no") && !deliveryN) {
                ristorantiFiltrati.remove(ristorante);
            }
        }

        return ristorantiFiltrati;
    }
}
