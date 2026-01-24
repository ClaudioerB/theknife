package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVWriter;

public class GestoreRecensioni {
    ArrayList<Recensione> recensioni;
    private String filePath;
    private static GestoreRecensioni gestoreRecensioni;

    private GestoreRecensioni() {
        gestoreRecensioni = this;
        filePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\recensioni.CSV";
        this.recensioni = new ArrayList<Recensione>();
        inserimentoDati();
    }

    public static GestoreRecensioni getGestoreRecensioni() {
        if(gestoreRecensioni == null) {
            gestoreRecensioni = new GestoreRecensioni();
        }
        return gestoreRecensioni;
    }

    
    public GestoreRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
        scriviFile();
    }

    public void rimuoviRecensione(int i) {
        if (recensioni.isEmpty()) {
            System.out.println("Non è presente nessun commento.");
        }
        else {
            recensioni.remove(i);
            scriviFile();
        }
    }

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

    private void scriviFile() {
        
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

    public ArrayList<Recensione> getRecensioniRistorante(String string) {
        ArrayList<Recensione> recensioniRistorante = new ArrayList<Recensione>();
        for (Recensione recensione : recensioni) {
            if (recensione.getId().equals(string)) {
                recensioniRistorante.add(recensione);
            }
        }
        return recensioniRistorante;
    }

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
