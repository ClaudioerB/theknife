package com.theknife;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Gestore {

    public Gestore() {
        start();
    }

    private void start() {
        GestoreDataset gestore = new GestoreDataset();
        // ArrayList dataSet = gestore.getDataSet();
        //GestoreUtenti gestoreUtenti = new GestoreUtenti();
        //testUtenti();
        //GestoreRecensioni gestore = new GestoreRecensioni();
        //testRecensioni();
        testDataset(gestore);
    }

    private void testRecensioni() {
        GestoreRecensioni gestoreRecensioni = new GestoreRecensioni();
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
    }
    
    private void testUtenti(){
        GestoreUtenti gestoreUtenti= new GestoreUtenti();
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
    }
    
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
      String[] rigaSplit=new String[13];
      switch (n){
      case 1:
      for (int i = 0; i < 13; i++) {
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
