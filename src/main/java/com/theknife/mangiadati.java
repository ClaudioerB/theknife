package com.theknife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class mangiadati {

    private InputStream inputStream;

    public mangiadati(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void caricaEProcessaCSV() {
        String line;
        String csvSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            // Leggi la prima riga per ottenere l'intestazione
            String header = br.readLine();
            String[] headers = header.split(csvSplitBy);
            int statoIndex = -1;

            // Trova l'indice della colonna "stato"
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equalsIgnoreCase("stato")) {
                    statoIndex = i;
                    break;
                }
            }

            if (statoIndex == -1) {
                System.out.println("Colonna 'stato' non trovata nel file CSV.");
                return;
            }

            // Leggi le righe successive
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                if (values.length > statoIndex) {
                    String stato = values[statoIndex].trim();
                    String content = line; // Contenuto da scrivere nel file

                    // Crea la cartella se non esiste
                    File directory = new File(stato);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    // Scrivi il contenuto in un file nella cartella
                    try (FileWriter writer = new FileWriter(new File(directory, "output.txt"), true)) {
                        writer.write(content + System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println("Errore durante la scrittura del file: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file CSV: " + e.getMessage());
        }
    }
}