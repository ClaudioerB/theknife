package com.theknife;


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
        //Gestore gestore =new Gestore();
        Grafica.main(args);

        //test(gestore);
        //System.out.println(System.getProperty("user.dir")+"/src/main/resources/Dataset/datafiles/dataset_sanificato_2.csv");
    }
    
}
