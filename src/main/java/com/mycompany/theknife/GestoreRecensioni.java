package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVWriter;

/**
 * @author TheKnifeTeam
 * 
 * GestoreRecensioni è la classe che gestisce il dataset delle recensioni.
 * Utilizza OpenCSV per la lettura e scrittura dei file CSV.
 * 
 * Le funzionalità includono l'aggiunta, la rimozione e la modifica di righe nel dataset delle recensioni.
 * Il dataset viene caricato da un file CSV all'avvio e salvato su file ad ogni modifica.
 * 
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class GestoreRecensioni {
    ArrayList<Recensione> recensioni;
    private String filePath;
    private static GestoreRecensioni gestoreRecensioni;
    /**
     * Costruttore della classe GestoreRecensioni che carica il dataset delle recensioni dal file CSV
     * Chiama il metodo inserimentoDati per caricare il dataset delle recensioni.
     */
    private GestoreRecensioni() {
        gestoreRecensioni = this;
        filePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\recensioni.CSV";
        this.recensioni = new ArrayList<Recensione>();
        inserimentoDati();
    }
    /**
     * Metodo per ottenere l'istanza della classe GestoreRecensioni
     * @return istanza della classe GestoreRecensioni
     */
    public static GestoreRecensioni getGestoreRecensioni() {
        if(gestoreRecensioni == null) {
            gestoreRecensioni = new GestoreRecensioni();
        }
        return gestoreRecensioni;
    }

    /**
     * Costruttore della classe GestoreRecensioni che prende come parametro un array di recensioni.
     * @param recensioni array di recensioni
     */
    public GestoreRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
    /**
     * Metodo per ottenere il dataset delle recensioni.
     * @return dataset delle recensioni
     */
    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }
    /**
     * Metodo per impostare il dataset delle recensioni.
     * @param recensioni lista di recensioni da impostare
     */
    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
    /**
     * Metodo per aggiungere una recensione al dataset.
     * Chiama il metodo scriviFile per salvare il dataset su file.
     * @param recensione recensione da aggiungere
     */
    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
        scriviFile();
    }
    /**
     * Metodo per rimuovere una recensione dal dataset.
     * Chiama il metodo scriviFile per salvare il dataset su file.
     * @param i indice della recensione da rimuovere
     */
    public void rimuoviRecensione(int i) {
        if (recensioni.isEmpty()) {
            System.out.println("Non è presente nessun commento.");
        }
        else {
            recensioni.remove(i);
            scriviFile();
        }
    }
    /**
     * Metodo per modificare una recensione nel dataset.
     * Chiama il metodo modificaRecensioneById per modificare la recensione con l'indice specificato.
     * @param recensione recensione da impostare
     * @param rVecchia recensione da modificare
     */
    public void modificaRecensione(Recensione recensione, Recensione rVecchia) {

        String utente = rVecchia.getUtenteRecensione();
        String data = rVecchia.getData();
        String time = rVecchia.getOra();
        int id = 0;
        if (recensione != null && rVecchia != null) {
            for (int i=1; i<recensioni.size(); i++) {
                Recensione rec = recensioni.get(i);
                if(rec.utenteRecensione.equals(utente)&& rec.getData().equals(data) && rec.getOra().equals(time)) {
                    id = i;
                    break;
                }
            }
        }
        if (id == 0) {
            //non vi è nussna recnsione possibile
        } else {
            modificaRecensioneById(id, recensione);
        }
    }
    /**
     * Metodo per modificare una recensione nel dataset.
     * Chiama il metodo scriviFile per salvare il dataset su file.
     * @param id indice della recensione da modificare
     * @param recensione recensione da impostare
     */
    public void modificaRecensioneById(int id, Recensione recensione) {
        if (recensioni.isEmpty()) {
            System.out.println("Non è presente nessun commento.");
        }
        else {
            String date = recensione.getData(), time = recensione.getOra(), txt = recensione.getRecensione(),title=recensione.getTitolo();
            double star=recensione.getStelle();
            recensioni.get(id).setRecensione(txt);
            recensioni.get(id).setData(date);
            recensioni.get(id).setOra(time);
            recensioni.get(id).setStelle(star);
            recensioni.get(id).setTitolo(title);
            scriviFile();
        }
    }
    /**
     * Metodo per stampare le recensioni nel dataset.
     */
    public void printRecensioni() {
        if (!recensioni.isEmpty()) {
            for (Recensione recensione : recensioni) {
                System.out.println("String utenteRecensione, int id, String testoRecensione, int stelle, String data, String ora");
                System.out.println(recensione.getUtenteRecensione()+" "+recensione.getStelle()+" "+recensione.getRecensione()+" "+recensione.getData()+" "+recensione.getOra()+"\n");
            }
        }
        else {
            System.out.println("\nAl momento non ci sono recensioni disponibili!\n");
        }
    }
    /**
     * Metodo per salvare il dataset delle recensioni su file CSV.
     * Utilizza CSVWriter per la scrittura del file CSV.
     */
    private void scriviFile() {
        GestoreDataset gestoreDataset = GestoreDataset.getGestoreDataset();
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
        ';',       // separatore personalizzato
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {
        String[] riga = new String[8];
        riga= new String[] {"Idristorante","IDpersona","Titolo","Stelle","Testo","Data","Ora","Risposta"};
        writer.writeNext(riga);
        for (int i=0; i<recensioni.size(); i++){
            riga[0] = recensioni.get(i).getId();
            //gestoreDataset.controllaDatasetRecensioniById(riga[0]);
            riga[1] = recensioni.get(i).getUtenteRecensione();
            riga[2] = recensioni.get(i).getTitolo();
            riga[3] = String.valueOf(recensioni.get(i).getStelle());
            riga[4] = recensioni.get(i).getRecensione();
            riga[5] = recensioni.get(i).getData();
            riga[6] = recensioni.get(i).getOra();
            riga[7] = recensioni.get(i).getRisposta();
            writer.writeNext(riga);
        }
        writer.flush();

        System.out.println("CSV scritto con successo!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    /**
     * Metodo per caricare il dataset delle recensioni dal file CSV.
     * Utilizza BufferedReader per la lettura del file CSV.
     */
    private void inserimentoDati() { 
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null ) {
                if(firstLine) {
                    firstLine = false;
                    continue; // Salta l'intestazione
                }
                recensioni.add(new Recensione());
                appoggio = line.split(";");
                recensioni.get(iRow).setId(appoggio[0]); //Poi bisogna creare il metodo dell'ID
                recensioni.get(iRow).setUtenteRecensione(appoggio[1]);
                recensioni.get(iRow).setTitolo(appoggio[2]);
                recensioni.get(iRow).setStelle(Double.parseDouble(appoggio[3]));
                recensioni.get(iRow).setRecensione(appoggio[4]);
                recensioni.get(iRow).setData(appoggio[5]);
                recensioni.get(iRow).setOra(appoggio[6]);
                if(appoggio.length==8)
                    recensioni.get(iRow).setRisposta(appoggio[7]);
                iRow++;
            }
        } catch (IOException e) {
            System.out.println("File non trovato.");
        }
    }
    /**
     * Metodo che restituisce il numero di righe nel dataset delle recensioni.
     * Utilizza BufferedReader per la lettura del file CSV.
     * @return numero di recensioni nel dataset
     */
    public int numeroRighe() {
        
        
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
    /**
     * Metodo per ottenere le recensioni di un ristorante specifico dal dataset.
     * @param string ID del ristorante
     * @return recensioni del ristorante
     */
    public ArrayList<Recensione> getRecensioniRistorante(String string) {
        ArrayList<Recensione> recensioniRistorante = new ArrayList<Recensione>();
        for (Recensione recensione : recensioni) {
            if (recensione.getId().equals(string)) {
                recensioniRistorante.add(recensione);
            }
        }
        return recensioniRistorante;
    }
    /**
     * Metodo per ottenere le recensioni di un utente specifico dal dataset.
     * @param username username dell'utente
     * @return recensioni dell'utente
     */
    public ArrayList<Recensione> getRecensioniByUsername(String username) {
        ArrayList<Recensione> recensioniRistorante = new ArrayList<Recensione>();
        for (Recensione recensione : recensioni) {
            if (recensione.getUtenteRecensione().equals(username)) {
                recensioniRistorante.add(recensione);
            }
        }
        return recensioniRistorante;
    }
}
