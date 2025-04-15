package com.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestoreDataset {
    private String[][] dataSet;
    private String filePath;
    
    public GestoreDataset() {
        filePath = System.getProperty("user.dir")+"/src/main/resources/Dataset/datafiles/dataset_ristoranti.csv"; // o "data/file.txt" se il file si trova in una sottodirectory
        dataSet=new String[numeroRighe()][13];
        inserimentoDati();
        System.out.println(dataSet[0][0]);
        System.out.println(dataSet[0][1]);

    }

    public String[][] getDataSet() {
        return dataSet;
    }

    public void setDataSet(String[][] dataSet) {
        this.dataSet = dataSet;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void inserimentoDati() {
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appoggio = line.split(";");
                for(int i = 0; i<13;i++){
                    dataSet[iRow][i] = appoggio[i];
                }
                iRow++;
            }
        } catch (IOException e) {
            System.out.println("File non trovato.");
        }
    }
    
    private int numeroRighe() {
        
        
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