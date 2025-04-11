package com.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestoreDataset {
    public String[][]dataSet;
    public GestoreDataset() {
        dataSet=new String[numeroRighe()][13];
    }

    private int numeroRighe() {
        String HOME = System.getProperty("user.dir");
        String filePath = HOME+"/src/main/resources/Dataset/datafiles/dataset_ristoranti.csv";
        int righe = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            
            while ((reader.readLine()) != null) {
                righe++;
            }
            
        } catch (IOException e) {
            System.out.println("File non trovato.");
        }
        return righe;
    }
    public String[] ricercaGenerale(String input, String dati[][]) {
        String[] risultati = new String[20];
        for (int i = 0; i < dati.length; i++) {
            for (int j = 0; j < dati[i].length; j++) {
                if (dati[i][j].toLowerCase().contains(input.toLowerCase())) {
                    System.out.println("Trovato: " + dati[i][j]);
                }
            }
        }
        return risultati;
    }
}