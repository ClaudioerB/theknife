package com.theknife;

import java.util.Scanner;

public class Gestore {

    public Gestore() {
        start();
    }

    private void start() {
        //estoreDataset gestore = new GestoreDataset();
        //ArrayList dataSet = gestore.getDataSet();
        GestoreUtenti gestoreUtenti= new  GestoreUtenti();
        testUtenti();
    }
    private void testUtenti(){
        GestoreUtenti gestoreUtenti= new  GestoreUtenti();
        int n;
        do { 
            
        Scanner input = new Scanner(System.in);
        System.out.println("premere 1 per aggiungere un utente");
        System.out.println("premere 2 per eliminare un utente");
        System.out.println("premere qualsiasi numero printare il dataset");
         n=Integer.parseInt(input.nextLine());
        
        switch (n){ 
            case 1:
                Utente utente = new Utente("nome", "cognome", "username", "nick", "passwordHash", "città", "indirizzo", "numeroCivico", "latitudine", "longitudine");
                
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
    
    /*public static void testDataset(GestoreDataset gestore) {
        int n;
        do { 
            
        
        System.out.println("premere 1 per aggiungere una riga");
        System.out.println("premere 2 per eliminare una riga");
        System.out.println("premere qualsiasi numero printare il dataset");
         n=Integer.parseInt(InputStr());
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
                Scanner input = new Scanner(System.in);
                
                gestore.eliminaRiga(input.nextInt());
                break;
            default:
                gestore.printDataSet();
        }
    } while (n!=0);
    }*/
}
