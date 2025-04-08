package com.theknife;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    
    public static void SOP(String s) {
        System.out.println(s);
    }

    static String InputStr() {
        return input.nextLine();
    }

    public static void main(String[] args) {
        InputStream ius = Main.class.getClassLoader().getResourceAsStream("/Dataset/datafiles/dataset_sanificato_2.csv");
        mangiadati ma = new mangiadati(ius);
        ma.caricaEProcessaCSV();
    }
}