package com.mycompany.theknife;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author TheKnifeTeam
 * 
 * Gestore è la classe principale che avvia il programma e gestisce le operazioni sui dataset, utenti e recensioni.<br>
 * Utilizza GestoreDataset, GestoreUtenti e GestoreRecensioni per manipolare i dati.<br>
 * 
 * Le funzionalità includono l'aggiunta, la rimozione e la modifica di righe nei dataset, utenti e recensioni.
 * Il programma interagisce con l'utente tramite la console per eseguire le operazioni desiderate.<br>
 * <br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class Gestore {
    private Utente utenteLoggato;
    private static Gestore gestore;
    /**
     * Costruttore della classe Gestore che avvia il programma<br>
     */
    private Gestore() {
        start();
    }
    /**
     * Metodo per ottenere l'istanza unica di Gestore<br>
     * @return istanza unica di Gestore
     */
    public static Gestore getGestore() {
        if(gestore == null) {
            gestore = new Gestore();
        }
        return gestore;
    }
    /**
     * Metodo privato che avvia il programma e gestisce le operazioni sui dataset, utenti e recensioni
    */
    private void start() {
        GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
        ArrayList dataSet = gestoreDataset.getDataSet();
        GestoreUtenti gestoreUtenti = GestoreUtenti.getGestoreUtenti();
        //testUtenti();
        GestoreRecensioni gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        GestoreRicerche gestoreRicerche = GestoreRicerche.getGestoreRicerche();
        //testRecensioni();
        //testDataset(gestore);
    }
    /**
     * Metodo privato per testare le funzionalità di gestione degli utenti
     * @return istanza unica di Gestore
     */
    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    /**
     * Metodo per impostare l'utente loggato<br>
     * @param utenteLoggato Utente loggato
     */
    public void setUtenteLoggato(Utente utenteLoggato) {
        this.utenteLoggato = utenteLoggato;
    }

    /**
     * Metodo pubblico che restituisce la data corrente formattata come stringa<br>
     * 
     * @return String della data in formato "dd-MMM-yyyy"
     */
    public String addData() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy"); //giorno-mese-anno 
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    
    /**
     * Metodo pubblico che restituisce l'ora corrente formattata come stringa<br>
     * 
     * @return String dell'ora in formato "HH:mm"
     */
    public String addTime() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");  //ore e minuti

        String formattedTime = myDateObj.format(myFormatObj);

        return formattedTime;
    }

    /**
     * Metodo pubblico per testare le funzionalità di gestione del dataset<br>
     * 
     * @param gestore Istanza di GestoreDataset da testare
     */
    public static void testDataset(GestoreDataset gestore) {
        Scanner input = new Scanner(System.in);
        int n;
        do {
            System.out.println("premere 1 per aggiungere una riga");
            System.out.println("premere 2 per eliminare una riga");
            System.out.println("premere qualsiasi numero printare il dataset");
            n=Integer.parseInt(input.nextLine());
            String[] rigaSplit=new String[17];
            switch (n){
                case 1:
                    for (int i = 0; i < 17; i++) {
                        rigaSplit[i]="a";
                    }
                    gestore.aggiungiRiga(rigaSplit);
                    System.out.println(gestore.numeroRighe()+"");
                    break;
                case 2:
                    System.out.println("inserire la riga da togliere");
                    gestore.eliminaRiga(input.nextInt());
                    break;
                default:
                    gestore.printDataSet();
            }
        } while (n!=0);
    }
}
