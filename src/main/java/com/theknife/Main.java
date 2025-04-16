package com.theknife;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    static String HOME = System.getProperty("user.dir");
    static Scanner input = new Scanner(System.in);
    
    public static void SOP(String s) {
        System.out.println(s);
    }

    static String InputStr() {
        return input.nextLine();
    }

    public static void main(String[] args) {
        GestoreDataset gestore = new GestoreDataset();
        ArrayList dataSet = gestore.getDataSet();
        test(gestore);
        //System.out.println(System.getProperty("user.dir")+"/src/main/resources/Dataset/datafiles/dataset_sanificato_2.csv");
    }
    public static void test(GestoreDataset gestore) {
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
    }
}
