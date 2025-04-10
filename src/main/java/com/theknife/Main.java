package com.theknife;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Carica il file usando un path relativo
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\dataset_sanificato_2.csv"; // o "data/file.txt" se il file si trova in una sottodirectory
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("File non trovato.");
            //System.out.println();
        }
    }
}
