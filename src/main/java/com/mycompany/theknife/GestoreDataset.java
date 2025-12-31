package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class GestoreDataset {
    
    private static ArrayList<String[]> dataSet;
    private String filePath;
    private static GestoreDataset gestoreDataset;

    public GestoreDataset() {
        filePath = System.getProperty("user.dir") + "\\theknife\\src\\main\\java\\com\\mycompany\\theknife\\data\\datasetRistoranti.csv"; // o "data/file.txt" se il file si trova in una sottodirectory
        dataSet= new ArrayList<String[]>();
        inserimentoDati();
        gestoreDataset = this;
    }

    public static GestoreDataset getGestoreDataset() {
        return gestoreDataset;
    }    

    public static ArrayList<String[]> getDataSet() {
        return dataSet;
    }



    public void setDataSet(ArrayList<String[]> dataSet) {
        this.dataSet = dataSet;
    }



    public void setDataSetPosition(int iRow, int iCol, String value) {
        this.dataSet.get(iRow)[iCol] = value;
        scriviFile();
    }

    public String getDataSetPosition(int iRow, int iCol) {
        return this.dataSet.get(iRow)[iCol];
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String[] getRiga(int iRow) {
        return dataSet.get(iRow);
    }

    public void setRiga(int iRow, String[] riga) {
        this.dataSet.set(iRow, riga);
        scriviFile();
    }

    public void eliminaRiga(int iRow) {
        this.dataSet.remove(iRow);
        scriviFile();
    }

    public void aggiungiRiga(String[] riga) {
        this.dataSet.add(riga);
        scriviFile();
    }
    private void scriviFile() {
        
           
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
        ';',       // separatore personalizzato
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {

        for (String[] riga : dataSet) {
            writer.writeNext(riga);
        }
        writer.flush();

        System.out.println("CSV scritto con successo!");

        } catch (IOException e) {
        e.printStackTrace();
        }
                

    }
    private void inserimentoDati() {
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                dataSet.add(new String[13]);
                appoggio = line.split(";");
                for(int i = 0; i<13;i++){
                    dataSet.get(iRow)[i] = appoggio[i];
                }
                iRow++;
            }
        } catch (IOException e) {
            System.out.println("File non trovato."+ e);
        }
    }
    
    public int numeroRighe() {
        
        
        int righe = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            
            while ((reader.readLine()) != null) {
                righe++;
            }
            
        } catch (IOException e) {
            System.out.println("File non trovato."+e);
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
    public void printDataSet() {
        for (String[] riga : dataSet) {
            for (String campo : riga) {
                System.out.print(campo + " ");
            }
            System.out.println();
        }
    }
    public void printDataSet(int iRow) {
        String[] riga = dataSet.get(iRow);
        for (String campo : riga) {
            System.out.print(campo + " ");
        }
        System.out.println();
    }
}