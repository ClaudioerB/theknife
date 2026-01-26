package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

/**
 * @author TheKnifeTeam
 * GestoreDataset è la classe che gestisce il dataset dei ristoranti.<br>
 * Utilizza OpenCSV per la lettura e scrittura dei file CSV.<br>
 * Le funzionalità includono l'aggiunta, la rimozione e la modifica di righe nei dataset.<br>
 * I dataset vengono caricati da un file CSV all'avvio e salvati su file ad ogni modifica.
 * I dataset che inizializza questa classe sono il dataset dei ristoranti e il dataset dei tipi di cucina.
 * Inoltre gestisce anche il calcolo della media delle valutazioni per ogni ristorante.<br><br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.<br>
 * @version 1.0
 */
public class GestoreDataset {
    
    private static ArrayList<String[]> dataSet;
    private String filePath;
    private static GestoreDataset gestoreDataset;
    private static String cucinePath;
    private static ArrayList<String> dataSetCucina;

    /**
     * Costruttore della classe GestoreDataset che carica il dataset dal file CSV.<br>
     * Inizializza la lista dataSet con le righe del file CSV.<br>
     */
    public GestoreDataset() {
        filePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\datasetRistoranti.csv"; 
        cucinePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\tipiCucine.csv";

        dataSet= new ArrayList<String[]>();
        dataSetCucina= new ArrayList<String>();
        inserimentoDati();
        inserimentoDatiCucina();
        gestoreDataset = this;
    }

    /**
     * Metodo che aggiunge le righe del file CSV al dataset.<br>
     */
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
                    String cucinaPulita = tipoCucina.trim();
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
        scriviFileCucina();
    }

    /**
     * Metodo che crea il file CSV per il dataset dei tipi di cucina.<br>
     * Utilizza OpenCSV per la scrittura del file CSV.<br>
     */
    public static void createCucineDataSet() {
        try {
            File myObj = new File(cucinePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che restituisce l'ID del ristorante corrispondente all'ID del ristorante fornito.<br>
     * @param idR ID del ristorante in formato String
     * @return ID della riga del ristorante nel dataset 
     */
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

    /**
     * Metodo che aggiunge le stelle del ristorante corrispondente all'ID del ristorante fornito.<br>
     * @param idR ID del ristorante in formato String
     */
    public void controllaDatasetRecensioniById(String idR) {
        GestoreRecensioni gestoreRecensioni = GestoreRecensioni.getGestoreRecensioni();
        ArrayList<Recensione> recensioni = gestoreRecensioni.getRecensioni();
        for (Recensione line : recensioni) {
            if (line.getId().equals(idR)) {
                addStelle(String.valueOf(line.getStelle()), idR);
            }
        }
    }

    /**
     * Metodo che modifica e aggiunge le stelle controllando le recensioni del ristorante.<br>
     * Utilizzato per controllare le valutazioni già presenti nel dataset delle recensioni.<br>
     */
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
    
    /**
     * Metodo che aggiunge le stelle del ristorante corrispondente all'ID del ristorante fornito.<br>
     * Riceve in input il valore della stella da aggiungere e l'ID del ristorante.<br>
     * Chiama il metodo setRiga per aggiornare il dataset con le nuove stelle.<br>
     * @param stella valutazione da aggiungere in formato String
     * @param idR ID del ristorante in formato String
     */
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

    /**
     * Metodo che modifica le stelle del ristorante corrispondente all'ID del ristorante fornito.<br>
     * Riceve in input il valore della stella da modificare, il valore della stella precedente e l'ID del ristorante.<br>
     * Chiama il metodo setRiga per aggiornare il dataset con la stella modificata.<br>
     * @param stellaNew valutazione nuova da aggiungere in formato String
     * @param stellaOld valutazione precedente da sostituire in formato String
     * @param idR ID del ristorante in formato String
     */
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

    /**
     * Metodo che rimuove le stelle del ristorante corrispondente all'ID del ristorante fornito.<br>
     * Riceve in input il valore della stella da rimuovere e l'ID del ristorante.<br>
     * Chiama il metodo setRiga per aggiornare il dataset con la stella rimossa.<br>
     * @param stella valutazione da rimuovere in formato String
     * @param idR ID del ristorante in formato String
     */
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

    /**
     * Metodo che calcola il valore medio delle stelle di un ristorante.<br>
     * Riceve in input il valore delle stelle da calcolare in formato String e ritorna il valore medio delle stelle.<br>
     * @param stelle valutazioni in formato String
     * @return valore medio delle stelle in numero decimale
     */
    public double calcStelle(String stelle) {
        String[] stars;
        double value = 0;
        int count = 0;
        double tot;
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
    }

    /**
     * Metodo che aggiunge una riga al dataset dei tipi di cucina.<br>
     * Chiama il metodo scriviFileCucina per scrivere il file CSV con la nuova riga.<br>
     * @param riga riga da aggiungere al dataset
     */
    public void aggiungiRigaCucina(String riga) {
        this.dataSetCucina.add(riga);
        scriviFileCucina();
    }

    /**
     * Metodo che scrive il file CSV con le righe del dataset dei tipi di cucina.<br>
     * Utilizza CSVWriter per la scrittura del file CSV.<br>
     */
    private void scriviFileCucina() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(cucinePath),
        ';', 
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {

        for (String riga : dataSetCucina) {
            String[] rigaArray = {riga};
            writer.writeNext(rigaArray);
        }
        writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che carica il dataset dei tipi di cucina dal file CSV nella variabile dataSetCucina.<br>
     * Utilizza BufferedReader per la lettura del file CSV.<br>
     */
    private void inserimentoDatiCucina() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cucinePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataSetCucina.add(line);
            }
        } catch (IOException e) {
            System.out.println("File non trovato."+ e);
        }
    }

    /**
     * Metodo che restituisce l'istanza di GestoreDataset. Se l'istanza non esiste, viene creata una nuova istanza.<br>
     * @return istanza di GestoreDataset
     */
    public static GestoreDataset getGestoreDataset() {
        if (gestoreDataset == null) {
            gestoreDataset = new GestoreDataset();
        }
        return gestoreDataset;
    }    

    /**
     * Metodo che restituisce il dataset dei ristoranti.<br>
     * @return dataset dei ristoranti
     */
    public static ArrayList<String[]> getDataSet() {
        return dataSet;
    }

    /**
     * Metodo che restituisce il dataset dei tipi di cucina.<br>
     * @return dataset dei tipi di cucina
     */
    public static ArrayList<String> getDataSetCucina() {
        return dataSetCucina;
    }

    /**
     * Metodo che stampa una riga del dataset dei tipi di cucina.<br>
     * @param iRow indice della riga da stampare
     */
    public void printDataSetCucina(int iRow) {
        String riga = dataSetCucina.get(iRow);
        System.out.println(riga);
    }

    /**
     * Metodo che ordina il dataset dei ristoranti in base alla città e al stato dell'utente loggato.<br>
     * @param utenteLoggato utente loggato
     */
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

    /**
     * Metodo che stampa il dataset dei ristoranti in base alla città e allo stato dell'utente loggato.<br>
     */
    private void printDatasetStatoCitta(){
        for (int i = 1; i < 30; i++) {
            String[] riga = dataSet.get(i);
            if (riga.length > 3) {
                System.out.println("Stato: " + riga[2] + " - Città: " + riga[3]);
            }
        }
    }

    /**
     * Metodo che imposta il dataset dei ristoranti.<br>
     * @param dataSet dataset dei ristoranti
     */
    public void setDataSet(ArrayList<String[]> dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Metodo che imposta un valore in un ristorante e in campo di esso del dataset dei ristoranti.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con il campo modificato.<br>
     * @param iRow indice del ristorante nel dataset
     * @param iCol indice del campo nel ristorante
     * @param value valore da impostare
     */
    public void setDataSetPosition(int iRow, int iCol, String value) {
        this.dataSet.get(iRow)[iCol] = value;
        scriviFile();
    }

    /**
     * Metodo che restituisce un valore in un ristorante e in campo di esso del dataset dei ristoranti.<br>
     * @param iRow indice del ristorante nel dataset
     * @param iCol indice del campo nel ristorante
     * @return valore del campo nel ristorante
     */
    public String getDataSetPosition(int iRow, int iCol) {
        return this.dataSet.get(iRow)[iCol];
    }

    /**
     * Metodo che restituisce il percorso del file CSV dei ristoranti.<br>
     * @return percorso del file CSV dei ristoranti.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Metodo che imposta il percorso del file CSV dei ristoranti.<br>
     * @param filePath percorso del file CSV dei ristoranti
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Metodo che restituisce una riga del dataset dei ristoranti.<br>
     * @param iRow indice della riga del dataset
     * @return ristorante nel dataset
     */
    public String[] getRiga(int iRow) {
        return dataSet.get(iRow);
    }

    /**
     * Metodo che imposta una riga del dataset dei ristoranti.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con la riga modificata.<br>
     * @param iRow indice della riga nel dataset
     * @param riga ristorante da impostare
     */
    public void setRiga(int iRow, String[] riga) {
        this.dataSet.set(iRow, riga);
        scriviFile();
    }

    /**
     * Metodo che elimina una riga del dataset dei ristoranti.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV senza la riga eliminata.<br>
     * @param iRow indice della riga nel dataset da eliminare
     */
    public void eliminaRiga(int iRow) {
        this.dataSet.remove(iRow);
        scriviFile();
    }

    /**
     * Metodo che aggiunge una riga al dataset dei ristoranti.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con la nuova riga.<br>
     * @param riga ristorante da aggiungere al dataset
     */
    public void aggiungiRiga(String[] riga) {
        this.dataSet.add(riga);
        scriviFile();
    }

    /**
     * Metodo che restituisce l'ultimo id utilizzato nel dataset dei ristoranti.<br>
     * @return ultimo id utilizzato
     */
    public int LastId(){
        int maxId = 0;
        for (int i = 1; i < dataSet.size(); i++) {
            try {
                int id = Integer.parseInt(dataSet.get(i)[16]);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException e) {}
        }
        return maxId+1;
    }

    /**
     * Metodo che scrive il dataset dei ristoranti nel file CSV.<br>
     * Utilizza la libreria CSVWriter per scrivere il file CSV.<br>
     */
    private void scriviFile() {
        if (dataSet == null || dataSet.isEmpty()) return;
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
        ';', 
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {

        for (String[] riga : dataSet) {
            writer.writeNext(riga);
        }
        writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che carica il dataset dei ristoranti dal file CSV nella variabile dataSet.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     */
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

    /**
     * Metodo che restituisce il numero di righe nel dataset dei ristoranti.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     * @return numero di ristoranti nel dataset
     */
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

    /**
     * Metodo che effettua una ricerca generale nel dataset dei ristoranti.<br>
     * @param input stringa da cercare nel dataset
     * @param dati matrice di dati in cui cercare
     * @return array di stringhe con i risultati della ricerca
     */
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

    /**
     * Metodo che stampa il dataset dei ristoranti.<br>
     */
    public void printDataSet() {
        for (String[] riga : dataSet) {
            for (String campo : riga) {
                System.out.print(campo + " ");
            }
            System.out.println();
        }
    }

    /**
     * Metodo che stampa una riga specifica del dataset dei ristoranti.<br>
     * @param iRow indice del ristorante da stampare
     */
    public void printDataSet(int iRow) {
        String[] riga = dataSet.get(iRow);
        for (String campo : riga) {
            System.out.print(campo + " ");
        }
        System.out.println();
    }

    /**
     * Metodo che elimina un ristorante dal dataset dei ristoranti.<br>
     * @param idR indice del ristorante da eliminare
     */
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
            eliminaRiga(id);
        } else {
            System.out.println("Non è stato trovato il ristorante nel dataset");
        }
    }
}