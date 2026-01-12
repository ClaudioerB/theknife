package com.mycompany.theknife;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestore {
    private Utente utenteLoggato;
    private static Gestore gestore;
    private Gestore() {
        start();
    }
    public static Gestore getGestore() {
        if(gestore == null) {
            gestore = new Gestore();
        }
        return gestore;
    }

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
    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public void setUtenteLoggato(Utente utenteLoggato) {
        this.utenteLoggato = utenteLoggato;
    }

    /*private void testRecensioni() {
        GestoreRecensioni gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        int n;
        do {

            Scanner input = new Scanner(System.in);
            System.out.println("premere 1 per aggiungere una recensione");
            System.out.println("premere 2 per eliminare una recensione");
            System.out.println("premere 3 per modificare una recensione");
            System.out.println("premere qualsiasi numero printare il dataset");
            n = Integer.parseInt(input.nextLine());

            switch (n) {
                case 1:
                    Recensione recensione = new Recensione(0,"utenteRecensione","testoRecensione",0, addData(), addTime());

                    gestoreRecensioni.aggiungiRecensione(recensione);
                    System.out.println(gestoreRecensioni.numeroRighe() + "");
                    break;
                case 2:
                    System.out.println("inserire la riga da togliere");

                    gestoreRecensioni.rimuoviRecensione(input.nextInt());
                    break;
                case 3:
                    System.out.println("inserire la riga da modificare");
                    int id = input.nextInt();

                    gestoreRecensioni.modificaRecensione(id, addData(), addTime());
                    break;
                default:
                    gestoreRecensioni.printRecensioni();
            }
        } while (n != 0);
    }*/
    
    /*private void testUtenti(){
        GestoreUtenti gestoreUtenti= GestoreUtenti.getGestoreUtenti();
        int n;
        do {
            
            Scanner input = new Scanner(System.in);
            System.out.println("premere 1 per aggiungere un utente");
            System.out.println("premere 2 per eliminare un utente");
            System.out.println("premere qualsiasi numero printare il dataset");
            n=Integer.parseInt(input.nextLine());
    
            switch (n){
                case 1:
                    Utente utente = new Utente("nome", "cognome", "username", "nick","passwordHash", "città", "indirizzo", "numeroCivico", "latitudine","longitudine");
    
                    gestoreUtenti.aggiungiUtente(utente);
                    System.out.println(gestoreUtenti.numeroRighe()+"");
                    break;
                case 2:
                    System.out.println("inserire la riga da togliere");
    
                    gestoreUtenti.rimuoviUtente(input.nextInt());
                    break;
                default:
                    gestoreUtenti.printUtenti();
            }
        } while (n!=0);
    }*/
    
    public String addData() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy"); //giorno-mese-anno -> non so se lasciarlo così per i diversi paesi

        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }
    public String addTime() {
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");  //ore e minuti

        String formattedTime = myDateObj.format(myFormatObj);

        return formattedTime;
    }

    
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
