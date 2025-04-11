package com.theknife;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String HOME = System.getProperty("user.dir");
    static Scanner input = new Scanner(System.in);
    
    public static void SOP(String s) {
        System.out.println(s);
    }

    static String InputStr() {
        return input.nextLine();
    }

    public static void main(String[] args) {
        String[][] mat = new String[17738][13];
        String filePath = HOME+"/src/main/resources/Dataset/datafiles/dataset_ristoranti.csv"; // o "data/file.txt" se il file si trova in una sottodirectory
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appoggio = line.split(";");
                for(int i = 0; i<13;i++){
                    mat[iRow][i] = appoggio[i];
                }
                iRow++;
            }
            SOP(mat[2][0]);
        } catch (IOException e) {
            SOP("File non trovato.");
        }
        //System.out.println(System.getProperty("user.dir")+"/src/main/resources/Dataset/datafiles/dataset_sanificato_2.csv");
    }
}
