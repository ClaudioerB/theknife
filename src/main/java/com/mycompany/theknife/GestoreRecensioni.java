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
    private static GestoreRecensioni gestoreRecensioni = new GestoreRecensioni();

    private GestoreRecensioni() {
        gestoreRecensioni = this;
        filePath = System.getProperty("user.dir") + "/src/main/resources/Review/reviews.csv";
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

    public void modificaRecensione(int i, String date, String time) {
        if (recensioni.isEmpty()) {
            System.out.println("Non è presente nessun commento.");
        }
        else {
            System.out.println("Inserire il testo da modificare");
            //String txt = input.nextLine();
            String txt = "Testo moficiato";
            recensioni.get(i).setRecensione(txt);
            recensioni.get(i).setData(date);
            recensioni.get(i).setOra(time);
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
        String[] riga = new String[6];
        for (int i=0; i<recensioni.size(); i++){
            riga[0] = String.valueOf(recensioni.get(i).getId());
            riga[1] = recensioni.get(i).getUtenteRecensione();
            riga[2] = String.valueOf(recensioni.get(i).getStelle());
            riga[3] = recensioni.get(i).getRecensione();
            riga[4] = recensioni.get(i).getData();
            riga[5] = recensioni.get(i).getOra();
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
                recensioni.add(new Recensione());
                appoggio = line.split(";");
                recensioni.get(iRow).setId(Integer.parseInt(appoggio[0])); //Poi bisogna creare il metodo dell'ID
                recensioni.get(iRow).setUtenteRecensione(appoggio[1]);
                recensioni.get(iRow).setStelle(Integer.parseInt(appoggio[2]));
                recensioni.get(iRow).setRecensione(appoggio[3]);
                recensioni.get(iRow).setData(appoggio[4]);
                recensioni.get(iRow).setOra(appoggio[5]);
                
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
            if (recensione.getIdRistorante().equals(string)) {
                recensioniRistorante.add(recensione);
            }
        }
        return recensioniRistorante;
    }
}
