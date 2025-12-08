package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class GestoreUtenti {
    ArrayList<Utente> utenti;
    private String filePath;

    public GestoreUtenti() {
        filePath = System.getProperty("user.dir")+"/src/main/resources/Users/users.csv"; 
        this.utenti = new ArrayList<Utente>();
        inserimentoDati();
    }

    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    public Utente getUtenteSingolo(int i) {
        return utenti.get(i);
    }

    public void setUtenteSingolo(Utente utente, int i) {
        this.utenti.set(i, utente);

    }
    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
        scriviFile();
    }
    public void rimuoviUtente(int i) {
        utenti.remove(i);
        scriviFile();
    }
    public void printUtenti() {
        for (Utente utente : utenti) {
            System.out.println(utente.getNome()+" "+utente.getCognome()+" "+utente.getUsername()+" "+utente.getNick()+" "+utente.getPasswordHash()+" "+utente.getCittà()+" "+utente.getIndirizzo()+" "+utente.getNumeroCivico()+" "+utente.getLatitudine()+" "+utente.getLongitudine());
        }
    }
    private void scriviFile() {
        
           
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
        ';',       // separatore personalizzato
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {
        String[] riga = new String[10];
        for (int i=0; i<utenti.size(); i++){
            riga[0] = utenti.get(i).getNome();
            riga[1] = utenti.get(i).getCognome();
            riga[2] = utenti.get(i).getUsername();
            riga[3] = utenti.get(i).getNick();
            riga[4] = utenti.get(i).getPasswordHash();
            riga[5] = utenti.get(i).getCittà();
            riga[6] = utenti.get(i).getIndirizzo();
            riga[7] = utenti.get(i).getNumeroCivico();
            riga[8] = utenti.get(i).getLatitudine();
            riga[9] = utenti.get(i).getLongitudine();
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
                utenti.add(new Utente());
                appoggio = line.split(";");
                utenti.get(iRow).setNome(appoggio[0]);
                utenti.get(iRow).setCognome(appoggio[1]);  
                utenti.get(iRow).setUsername(appoggio[2]);
                utenti.get(iRow).setNick(appoggio[3]);
                utenti.get(iRow).setPasswordHash(appoggio[4]);
                utenti.get(iRow).setCittà(appoggio[5]);
                utenti.get(iRow).setIndirizzo(appoggio[6]);
                utenti.get(iRow).setNumeroCivico(appoggio[7]);
                utenti.get(iRow).setLatitudine(appoggio[8]);
                utenti.get(iRow).setLongitudine(appoggio[9]);
                
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
}
