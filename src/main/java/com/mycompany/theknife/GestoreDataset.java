package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class GestoreDataset {
    
    private static ArrayList<String[]> dataSet;
    private String filePath;
    private static GestoreDataset gestoreDataset;
    private static String cucinePath;
    private static ArrayList<String> dataSetCucina;

    public GestoreDataset() {
        filePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\datasetRistoranti.csv"; // o "data/file.txt" se il file si trova in una sottodirectory
        cucinePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\tipiCucine.csv";

        dataSet= new ArrayList<String[]>();
        dataSetCucina= new ArrayList<String>();
        inserimentoDati();

        inserimentoDatiCucina();
        //aggiungiRigheCucina();
        //scriviFileCucina();
        //controllaDatasetRecensioni();
        gestoreDataset = this;
    }

    private void aggiungiRigheCucina() {
        dataSetCucina.clear();
        boolean checkfirst = true;
        for (String[] row : this.dataSet) {
            if (checkfirst) {
                checkfirst = false;
                continue;
            }
            if (row[5].contains(",")) {
                String[] tipiCucina = row[5].split(",");
                for (String tipoCucina : tipiCucina) {
                    String cucinaPulita = tipoCucina.trim(); // Rimuovi spazi
                    if (!cucinaPulita.isEmpty() && !dataSetCucina.contains(cucinaPulita)) {
                        dataSetCucina.add(cucinaPulita);
                    }
                }
            } else {
                String cucinaPulita = row[5].trim();
                if (!cucinaPulita.isEmpty() && !dataSetCucina.contains(cucinaPulita)) {
                    dataSetCucina.add(cucinaPulita);
                }
            }
        }
        System.out.println("Cucine caricate nel dataset cucina");
        //this.dataSetCucina.add(riga);
        scriviFileCucina();
        System.out.println();
    }

    public static void createCucineDataSet() {
        try {
            File myObj = new File(cucinePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public int getId(String idR) {
        String[] row;
        int id = 0;
        for (int i=1; i<dataSet.size(); i++) {
            row = dataSet.get(i);
            if (row[16].equals(idR)) {
                id = i;
                break;
            }
        }
        return id;
    }
    public void controllaDatasetRecensioniById(String idR) {
        GestoreRecensioni gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        ArrayList<Recensione> recensioni = gestoreRecensioni.getRecensioni();
        for (Recensione line : recensioni) {
            if (line.getId().equals(idR)) {
                addStelle(String.valueOf(line.getStelle()), idR);
            }
        }
    }
    private void controllaDatasetRecensioni() {
        GestoreRecensioni gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        ArrayList<Recensione> recensioni = gestoreRecensioni.getRecensioni();
        for (Recensione line : recensioni) {
            String idR = line.getId();
            for (String[] row : dataSet) {
                if (row[16].equals(idR)) {
                    addStelle(String.valueOf(line.getStelle()), idR);
                }
            }
        }
    }
    /*public void countStelle(double value, String idR) {
        /*int count = 0;
        for (int i=0; i<value;i++) {
            count++;
        }
        double rating = (double) count;
        addStelle(String.valueOf(value), idR);
    }*/
    public void addStelle(String stella,String idR) {
        int id = getId(idR);
        String[] row = getRiga(id);
        if (!(row[13].isEmpty() || row[13] == null)) {
            row[13] = row[13] + ","+stella;
        } else {
            row[13] = stella;
        }
        setRiga(id, row);
    }
    public void changeStelle(String stellaNew, String stellaOld,String idR) {
        int id = getId(idR);
        String[] row = getRiga(id);
        if (!(row[13].isEmpty() || row[13] == null)&&row[13].contains(stellaOld)) {
            row[13].replaceFirst(stellaOld, stellaNew);
        } else {
            System.out.println("Non è presente nessuna stella");
        }
        setRiga(id, row);
    }
    public void removeStelle(String stella,String idR) {
        int id = getId(idR);
        String[] row = getRiga(id);
        if (!(row[13].isEmpty() || row[13] == null)&&row[13].contains(stella)) {
            if (row[13].contains(",")) {
                row[13] = row[13].replaceFirst(stella+",", "");
            } else {
                row[13] = "0";
            }
        } else {
            System.out.println("Non è presente nessuna stella");
        }
        setRiga(id, row);
    }
    public double calcStelle(String stelle) {
        String[] stars;
        double value = 0;
        int count = 0;
        double tot;
        //(double) Math.round(valore1);
        //Math.round(valore * 2) / 2.0;
        if (stelle == null || stelle.isEmpty()) {
            return 0.0;
        } else {
            if (stelle.contains(",")) {
                stars = stelle.trim().split(",");
                for (String row : stars) {
                    value = value + Double.parseDouble(row);
                    count++;
                }
                tot = value / (double) count;
            } else {
                count ++;
                value = Double.parseDouble(stelle.trim());
                tot = value / (double) count;
            }
        }
        
        return Math.round(tot);
        //return Math.round(tot * 2) / 2.0;

        //return tot;
        /*
        if (stelle.contains(",")) {
            stars = stelle.trim().split(",");
            for (String row : stars) {
                value = value + Double.parseDouble(row);
                count++;
            }
            tot = value / (double) count;
        } else {
            count ++;
            value = Double.parseDouble(stelle.trim());
            tot = value / (double) count;
        }
        return Math.round(tot * 2) / 2.0; */
    }

    public void aggiungiRigaCucina(String riga) {
        this.dataSetCucina.add(riga);
        scriviFileCucina();
    }
    private void scriviFileCucina() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(cucinePath),
        ';',       // separatore personalizzato
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {

        for (String riga : dataSetCucina) {
            String[] rigaArray = {riga};
            writer.writeNext(rigaArray);
        }
        writer.flush();
        System.out.println("CSV scritto con successo!");
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    private void inserimentoDatiCucina() {
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(cucinePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataSetCucina.add(line);
            }
        } catch (IOException e) {
            System.out.println("File non trovato."+ e);
        }
    }

    public static GestoreDataset getGestoreDataset() {
        if (gestoreDataset == null) {
            gestoreDataset = new GestoreDataset();
        }
        return gestoreDataset;
    }    

    public static ArrayList<String[]> getDataSet() {
        return dataSet;
    }
    public static ArrayList<String> getDataSetCucina() {
        return dataSetCucina;
    }
    public void printDataSetCucina(int iRow) {
        String riga = dataSetCucina.get(iRow);
        System.out.println(riga);
    }
    public void ordinaDataSet(Utente utenteLoggato) {
        ArrayList<String[]> Appoggio = new ArrayList<>(dataSet);
        ArrayList<String[]> cittàDataSet = new ArrayList<>();
        ArrayList<String[]> statoDataSet = new ArrayList<>();
        ArrayList<String[]> nuovoDataSet = new ArrayList<>();

        for (String[] riga : Appoggio) {
            if(riga[3].equals(utenteLoggato.getCittà())){
                cittàDataSet.add(riga);
                
            }
            else{
                if(riga[2].contains(utenteLoggato.getStato())){
                    statoDataSet.add(riga);
                    
                }
            }
        }
        nuovoDataSet.addAll(cittàDataSet);
        
        nuovoDataSet.addAll(statoDataSet);
        ArrayList<String[]> preferitiUtente = utenteLoggato.getPreferiti();
        if(preferitiUtente!=null){
            preferitiUtente.removeAll(nuovoDataSet);
            nuovoDataSet.addAll(preferitiUtente);
        }
        ArrayList<String[]> rimanenti = new ArrayList<>(dataSet);
        rimanenti.removeAll(nuovoDataSet);
        nuovoDataSet.addAll(rimanenti);
        setDataSet(nuovoDataSet); 
        
    }
    private void printDatasetStatoCitta(){
        for (int i = 1; i < 30; i++) { // Salta la prima riga (header)
            String[] riga = dataSet.get(i);
            if (riga.length > 3) {
                System.out.println("Stato: " + riga[2] + " - Città: " + riga[3]);
            }
        }
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
    public int LastId(){
        int maxId = 0;
        for (int i = 1; i < dataSet.size(); i++) { // Skip header
            try {
                int id = Integer.parseInt(dataSet.get(i)[16]);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException e) {
                // Handle non-integer, skip or log
            }
        }
        return maxId+1;
    }
    private void scriviFile() {
        if (dataSet == null || dataSet.isEmpty()) return;
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
                dataSet.add(new String[17]);
                appoggio = line.split(";");
                for(int i = 0; i<17;i++){
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
    public void removeRistoranteById(String idR) {
        String[] row;
        int id = 0;
        for (int i=1; i<dataSet.size(); i++) {
            row = dataSet.get(i);
            if (row[16].equals(idR)) {
                id = i;
                break;
            }
        }
        if (id!=0) {
            System.out.println("eliminata"+id);
            eliminaRiga(id);
        } else {
            System.out.println("Non è stato trovato il ristorante nel dataset");
        }
    }
}