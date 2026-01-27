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
 * 
 * GestoreUtenti è la classe che gestisce il dataset degli utenti.<br>
 * Utilizza OpenCSV per la lettura e scrittura dei file CSV.<br>
 * 
 * Le funzionalità includono l'aggiunta, la rimozione e la modifica di righe nei dataset.
 * I dataset vengono caricati da un file CSV all'avvio e salvati su file ad ogni modifica. <br>
 * Questa classe inizializza il dataset degli utenti e gestisce anche i dataset dei preferiti degli utenti e anche il dataset delle persone che possiedono un ristorante.<br>
 * <br>
 * Note: Alcuni metodi di test sono inclusi solo per scopi di debug.
 * 
 * @version 1.0
 */
public class GestoreUtenti {

    private ArrayList<Utente> utenti;    
    private String filePath;
    private String fileUserPath;
    private String personeRistorantiPath;
    private static GestoreUtenti gestoreUtenti;

    private static String pathFavourite;
    private static ArrayList<String[]> dataSetFavourite;

    private static ArrayList<String[]> personeRistoranti;

    /**
     * Costruttore della classe GestoreUtenti.<br>
     * Inizializza il dataset degli utenti, il dataset dei preferiti degli utenti e il dataset delle persone che possiedono un ristorante.<br>
     * Poi li carica da un file CSV e li salva su file ad ogni modifica.
     */
    private GestoreUtenti() {
        filePath = System.getProperty("user.dir")+"/src/main/resources/Users/users.csv"; 
        fileUserPath = System.getProperty("user.dir") + "/src/main/java/com/mycompany/theknife/data/datasetUtenti.CSV";
        pathFavourite = System.getProperty("user.dir") + "/src/main/java/com/mycompany/theknife/data/favourite.csv";
        personeRistorantiPath = System.getProperty("user.dir") + "/src/main/java/com/mycompany/theknife/data/personeRistoranti.CSV";
        if (!new File(filePath).exists()) {
            filePath = System.getProperty("user.dir")
                + "/../src/main/java/com/mycompany/theknife/data/datasetRistoranti.csv";
        }
        if (!new File(pathFavourite).exists()) {
            pathFavourite = System.getProperty("user.dir")
                + "/../src/main/java/com/mycompany/theknife/data/favourite.csv";
        }
        if (!new File(personeRistorantiPath).exists()) {
            personeRistorantiPath = System.getProperty("user.dir")
                + "/../src/main/java/com/mycompany/theknife/data/personeRistoranti.CSV";
        }
        if (!new File(fileUserPath).exists()) {
            fileUserPath = System.getProperty("user.dir")
                + "/../src/main/java/com/mycompany/theknife/data/datasetUtenti.CSV";
        }

        this.utenti = new ArrayList<Utente>();
        this.dataSetFavourite= new ArrayList<String[]>();
        personeRistoranti= new ArrayList<String[]>();
        gestoreUtenti = this;
        //inserimentoDati();
        inserimentoNewDati();

        //createFavouriteDataSet();
        inserimentoFavouriteDati();
        aggiungiRigheFavourite();
        //scriviFavouriteFile();
        //inserimentoFavouriteDati();

        //createPersoneRistorantiDataSet();
        inserimentoPersoneRistorantiDati();
        aggiungiRighePersoneRistoranti();
        //scriviPersoneRistorantiFile();
    }
    /**
     * Metodo che restituisce l'istanza unica della classe GestoreUtenti.<br>
     * 
     * @return l'istanza unica della classe GestoreUtenti
     */
    public static GestoreUtenti getGestoreUtenti() {
        if(gestoreUtenti == null) {
            gestoreUtenti = new GestoreUtenti();
        }
        return gestoreUtenti;
    }
    /**
     * Metodo che restituisce il dataset degli utenti.<br>
     * 
     * @return il dataset degli utenti
     */
    public ArrayList<Utente> getUtenti() {
        return utenti;
    }
    /**
     * Metodo che imposta il dataset degli utenti.<br>
     * @param utenti la lista degli utenti da impostare
     */
    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }
    /**
     * Metodo che restituisce il dataset dei preferiti degli utenti.<br>
     * @return il dataset dei preferiti degli utenti
     */
    public ArrayList<String[]> getFavourite() {
        return dataSetFavourite;
    }
    /**
     * Metodo che restituisce il dataset delle persone che possiedono un ristorante.<br>
     * @return il dataset delle persone che possiedono un ristorante
     */
    public ArrayList<String[]> getPersoneRistoranti() {
        return personeRistoranti;
    }
    /**
     * Restituisce l'utente con l'indice specificato.<br>
     * @param i l'indice dell'utente nel dataset
     * @return l'utente con l'indice specificato
     */
    public Utente getUtenteSingolo(int i) {
        return utenti.get(i);
    }
    /**
     * Imposta l'utente con l'indice specificato.<br>
     * @param utente l'utente da impostare
     * @param i l'indice dell'utente nel dataset
     */
    public void setUtenteSingolo(Utente utente, int i) {
        this.utenti.set(i, utente);

    }
    /**
     * Metodo che aggiunge un utente al dataset.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con l'utente aggiunto.<br>
     * @param utente l'utente da aggiungere
     */
    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
        scriviNewFile();
    }
    /**
     * Metodo che rimuove un utente dal dataset.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con l'utente rimosso.<br>
     * @param i l'indice dell'utente da rimuovere
     */
    public void rimuoviUtente(int i) {
        utenti.remove(i);
        scriviNewFile();
    }
    /**
     * Metodo che stampa tutti gli utenti nel dataset.<br>
     */
    public void printUtenti() {
        for (Utente utente : utenti) {
            System.out.println(utente.getNome()+" "+utente.getCognome()+" "+utente.getUsername()+" "+utente.getPasswordHash()+" "+utente.getCittà()+" "+utente.getIndirizzo());
        }
    }
    /**
     * Restituisce i preferiti dell'utente con lo username specificato.<br>
     * @param username lo username dell'utente
     * @return i preferiti dell'utente
     */
    public String getFavouriteByUsername(String username) {
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                return fav[1]; // Restituisce i preferiti dell'utente trovato
            }
        }
        return null; // Utente non trovato
    }
    /**
     * Restituisce il ristorante con l'id specificato.<br>
     * @param id L'indice del ristorante
     * @return il ristoratore che possie il ristorante
     */
    public String getPersoneRistorantiByIdRistorante(String id) {
        for (String[] row : personeRistoranti) {
            if (row[0].equals(id)) {
                return row[1]; 
            }
        }
        return null; // Utente non trovato
    }
    /**
     * Rimuove il ristorante con l'id specificato.<br>
     * Chiama il metodo scriviPersoneRistorantiFile per scrivere il file CSV con il ristorante rimosso.<br>
     * @param idR L'indice del ristorante
     * @param idU L'indice del ristoratore
     */
    public void removePersoneRistorantiByIdRistorante(String idR, String idU) {
        String[] row;
        int id = 0;
        for (int i=1; i<personeRistoranti.size(); i++) {
            row = personeRistoranti.get(i);
            if (row[0].equals(idR) && row[1].equals(idU)) {
                id = i;
                break;
            }
        }
        if (id!=0) {
            personeRistoranti.remove(id);
        } else {
            System.out.println("Non è stato trovato il ristorante con i ristoratori");
        }
        scriviPersoneRistorantiFile();
    }
    /**
     * Aggiunge un nuovo ristorante al dataset.<br>
     * Chiama il metodo scriviPersoneRistorantiFile per scrivere il file CSV con il ristorante aggiunto.<br>
     * @param idR L'indice del ristorante
     * @param idU L'indice del ristoratore
     */
    public void addNewPersoneRistoranti(String idU, String idR) {
        String[] rowNew = {idR,idU};
        for (int i = 0; i < personeRistoranti.size(); i++) {
            if (personeRistoranti.get(i)[0].equals(idR)) {
                String currentFavs = personeRistoranti.get(i)[1];
                if (currentFavs == null || currentFavs.isEmpty()) {
                    personeRistoranti.get(i)[1] = idU;
                }
                scriviPersoneRistorantiFile();
                return;
            }
        }
        personeRistoranti.add(rowNew);
        scriviPersoneRistorantiFile();
    }
    /**
     * Aggiunge un nuovo preferito al dataset.<br>
     * @param username lo username dell'utente
     * @param newFav Indice del nuovo ristorante preferito
     */
    public void addNewFavourite(String username, String newFav) {
        for (int i = 0; i < dataSetFavourite.size(); i++) {
            if (dataSetFavourite.get(i)[0].equals(username)) {
                String currentFavs = dataSetFavourite.get(i)[1];
                if (currentFavs == null || currentFavs.isEmpty()) {
                    dataSetFavourite.get(i)[1] = newFav;
                } else {
                    dataSetFavourite.get(i)[1] = currentFavs + "," + newFav;
                }
                scriviFavouriteFile();
                return;
            }
        }
    }
    /**
     * Aggiunge righe al dataset dei preferiti.<br>
     * Chiama il metodo scriviFavouriteFile per scrivere il file CSV con le righe aggiunte.<br>
     */
    private void aggiungiRigheFavourite() {
        boolean checkfirst = true;
        for (Utente row : this.utenti) {
            if (checkfirst) {
                checkfirst = false;
                continue;
            }
            String username = row.getUsername();
            boolean esisteGià = false;
            for (String[] entry : dataSetFavourite) {
                if (entry.length > 0 && entry[0].equals(username)) {
                    esisteGià = true;
                    break;
                }
            }
            if (!esisteGià) {
                dataSetFavourite.add(new String[]{username, ""});
            }
        }
        scriviFavouriteFile();
    }
    /**
     * Aggiunge righe al dataset dei ristoranti e ristoratori.<br>
     * Chiama il metodo scriviPersoneRistorantiFile per scrivere il file CSV con le righe aggiunte.<br>
     */
    private void aggiungiRighePersoneRistoranti() {
        boolean checkfirst = true;
        for (Utente row : this.utenti) {
            if (checkfirst) {
                checkfirst = false;
                continue;
            }
            String id = row.getId();
            boolean esisteGià = false;
            for (String[] entry : personeRistoranti) {
                if (entry.length > 0 && entry[0].equals(id)) {
                    esisteGià = true;
                    break;
                }
            }
            if (!esisteGià) {
                dataSetFavourite.add(new String[]{id, ""});
            }
        }
        scriviPersoneRistorantiFile();
    }
    /**
     * Crea il file dei preferiti.<br>
     * Utilizza FileWriter per la creazione del file CSV.<br>
     */
    public static void createFavouriteDataSet() {
        try {
            File myObj = new File(pathFavourite);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * Scrive il file dei preferiti.<br>
     * Utilizza OpenCSV per la scrittura del file CSV.<br>
     */
    private void scriviFavouriteFile() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(pathFavourite),
        ';',   
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {
        String[] riga = new String[2];
        for (int i=0; i<dataSetFavourite.size(); i++){
            riga[0] = dataSetFavourite.get(i)[0];
            riga[1] = dataSetFavourite.get(i)[1];
            writer.writeNext(riga);
        }
        writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo che scrive il dataset dei ristoranti e ristoratori nel file CSV.<br>
     * Utilizza la libreria CSVWriter per scrivere il file CSV.<br>
     */
    private void scriviPersoneRistorantiFile() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(personeRistorantiPath),
        ';',   
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {
        String[] riga = new String[2];
        for (int i=0; i<personeRistoranti.size(); i++){
            riga[0] = personeRistoranti.get(i)[0];
            riga[1] = personeRistoranti.get(i)[1];
            writer.writeNext(riga);
        }
        writer.flush();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    /**
     * Metodo che scrive il dataset degli utenti nel file CSV.<br>
     * Utilizza la libreria CSVWriter per scrivere il file CSV.<br>
     */
    private void scriviNewFile() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileUserPath),
        ';',      
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)) {
        String[] riga = new String[10];
        for (int i=0; i<utenti.size(); i++){
            riga[0] = utenti.get(i).getId();
            riga[1] = utenti.get(i).getUsername();
            riga[2] = utenti.get(i).getPasswordHash();
            riga[3] = utenti.get(i).getEmail();
            riga[4] = utenti.get(i).getNome();
            riga[5] = utenti.get(i).getCognome();
            riga[6] = utenti.get(i).getStato();
            riga[7] = utenti.get(i).getCittà();
            riga[8] = utenti.get(i).getIndirizzo();
            if (utenti.get(i).isRistoratore()) {
                riga[9] = "1";
            } else {
                riga[9] = "0";
            }
            writer.writeNext(riga);
        }
        writer.flush();
        System.out.println("CSV scritto con successo!");
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    /**
     * Metodo che restituisce il numero di righe del file CSV.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     * @return numero di righe
     */
    public int nRigheUtentiFile(){
        int righe = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileUserPath))) {
            while ((reader.readLine()) != null) {
                righe++;
            }
        } catch (IOException e) {
            System.out.println("File non trovato.");
        }
        return righe;
    }
    /**
     * Metodo che inserisce i dati dal dataset nella lista di utenti.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     */
    private void inserimentoNewDati() {
        String[] appoggio;
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileUserPath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                utenti.add(new Utente());
                appoggio = line.split(";");
                if(appoggio.length!=1){
                    utenti.get(iRow).setId(appoggio[0]);
                    utenti.get(iRow).setUsername(appoggio[1]);
                    utenti.get(iRow).setPasswordHash(appoggio[2]);
                    utenti.get(iRow).setEmail(appoggio[3]);
                    utenti.get(iRow).setNome(appoggio[4]);
                    utenti.get(iRow).setCognome(appoggio[5]);
                    utenti.get(iRow).setStato(appoggio[6]);
                    utenti.get(iRow).setCittà(appoggio[7]);
                    utenti.get(iRow).setIndirizzo(appoggio[8]);
                    if (appoggio[9].equals("1")) {
                        utenti.get(iRow).setRistoratore(true);
                    } else {
                        utenti.get(iRow).setRistoratore(false);
                    }
                }
                iRow++;
            }
        } catch (IOException e) {
            System.out.println("File non trovato.");
        }
    }
    /**
     * Metodo che inserisce i dati dal dataset dei ristoranti nella lista dei preferiti.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     */
    private void inserimentoFavouriteDati() {
        int iRow = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFavourite))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] appoggio = line.split(";", -1);
                if (appoggio.length >= 2) {
                    dataSetFavourite.add(new String[]{appoggio[0], appoggio[1]});
                } else if (appoggio.length == 1) {
                    dataSetFavourite.add(new String[]{appoggio[0], ""});
                } else {
                    System.out.println("Riga malformata ignorata: " + line);
                    continue;
                }
                iRow++;
            }
            //System.out.println("Caricati " + iRow + " favourites dal file");
        } catch (IOException e) {
            System.out.println("File favourite non trovato - verrà creato.");
        }
    }
    /**
     * Metodo che inserisce i dati dal dataset dei ristoranti e ristoratori nella lista personeRistoranti.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     */
    private void inserimentoPersoneRistorantiDati() {
        int iRow = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(personeRistorantiPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] appoggio = line.split(";", -1);
                if (appoggio.length >= 2) {
                    personeRistoranti.add(new String[]{appoggio[0], appoggio[1]});
                } else if (appoggio.length == 1) {
                    personeRistoranti.add(new String[]{appoggio[0], ""});
                } else {
                    System.out.println("Riga malformata ignorata: " + line);
                    continue;
                }
                iRow++;
            }
        } catch (IOException e) {
            System.out.println("File favourite non trovato - verrà creato.");
        }
    }
    /**
     * Metodo che restituisce il numero di righe del file CSV.<br>
     * Utilizza la libreria BufferedReader per la lettura del file CSV.<br>
     * @return numero di righe
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
     * Metodo che verifica le credenziali di un utente.<br>
     * @param username Username dell'utente da verificare
     * @param password Password dell'utente da verificare
     * @return true se le credenziali sono valide, altrimenti false
     */
    public boolean verificaCredenziali(String username, String password) {
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username) && utente.getPasswordHash().equals(password)) {
                return true; // Credenziali valide
            }
        }
        return false; // Credenziali non valide
    }
    /**
     * Metodo che aggiorna un utente nel dataset.<br>
     * Chiama il metodo scriviFile per scrivere il file CSV con l'utente aggiornato.<br>
     * @param utente l'utente da aggiornare
     */
    public void aggiornaUtente(Utente utente) {
        for (int i = 0; i < utenti.size(); i++) {
            if (utenti.get(i).getUsername().equals(utente.getUsername())) {
                utenti.set(i, utente);
                scriviNewFile(); 
                return;
            }
        }
    }
    /**
     * Metodo che controlla la validità della password.<br>
     * @param password la password dell'utente da controllare
     * @return true se la password e' valida, altrimenti false
     */
    public boolean controlloPassword(String password) {
        if (password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*")) {
            return true; // Password valida
        } else {
            return false; // Password non valida
        }
    }
    /**
     * Metodo che restituisce l'utente con lo username specificato.<br>
     * @param username lo username dell'utente
     * @return l'utente trovato
     */
    public Utente getUtenteByUsername(String username) {
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username)) {
                return utente; // Restituisce l'utente trovato
            }
        }
        return null; // Utente non trovato
    }
    /**
     * Metodo che crea un nuovo utente.<br>
     * Chiama il metodo aggiungiUtente per aggiungere l'utente al dataset.<br>
     * @param nuovoUtente l'utente da creare
     */
    public int creaUtente(Utente nuovoUtente) {
        // Verifica se l'username esiste già
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(nuovoUtente.getUsername())) {
                return 1; // Username già esistente
            }
            if(utente.getEmail().equals(nuovoUtente.getEmail())) {
                return 2; // Email già esistente
            }
            if(!controlloPassword(nuovoUtente.getPasswordHash())) {
                return 3; // Password non valida
            }
            if(!nuovoUtente.getEmail().contains("@"))
                return 4; // Email senza @
        }
        aggiungiUtente(nuovoUtente);
        return 0; // Utente creato con successo
    }
    /**
     * Metodo che aggiunge un utente ai preferiti.<br>
     * @param nuovoUtente l'utente da aggiungere
     */
    private void aggiungiUtentePreferiti(Utente nuovoUtente) {
        String[] preferiti = new String[2];
        preferiti[0] = nuovoUtente.getUsername();
        preferiti[1] = "";
        dataSetFavourite.add(preferiti);
        scriviFavouriteFile();
    }
    /**
     * Metodo che stampa l'elenco dei preferiti dell'utente con lo username specificato.<br>
     * @param username lo username dell'utente
     */
    public ArrayList<String[]> getPreferitiUtente(String username) {
        ArrayList<String[]> preferitiUtente = new ArrayList<>();
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                String[] preferitiArray = fav[1].split(",");
                for (String preferito : preferitiArray) {
                    if (!preferito.isEmpty()) {
                        GestoreRicerche gestoreRicerche=GestoreRicerche.getGestoreRicerche();
                        preferitiUtente.add(gestoreRicerche.trovaRistorantiID(preferito));
                    }
                }
                printPreferitiUtente();
                break; // Esci dal ciclo una volta trovato l'utente
            }
        }
        return preferitiUtente;
    }
    /**
     * Metodo che stampa l'elenco dei preferiti dell'utente loggato.<br>
     */
    public void printPreferitiUtente(){
        Gestore gestore=Gestore.getGestore();
        for (String[] string : gestore.getUtenteLoggato().getPreferiti()) {
            System.out.println(string[1]);
        }
    }
    /**
     * Metodo che aggiunge un preferito all'utente con lo username specificato.<br>
     * Chiama il metodo scriviFavouriteFile per aggiornare il file CSV dei preferiti.
     * @param username lo username dell'utente
     * @param ristorante il ristorante da aggiungere
     */
    public void aggiungiPreferitoUtente(String username, String[] ristorante) {
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                String currentFavs = fav[1];
                String ristoranteString = ristorante[16]; 
                if (currentFavs == null || currentFavs.isEmpty()) {
                    fav[1] = ristoranteString;
                } else {
                    fav[1] = currentFavs + "," + ristoranteString;
                }
                scriviFavouriteFile();
                return;
            }
        }
    }
    /**
     * Metodo che rimuove un preferito all'utente con lo username specificato.<br>
     * Chiama il metodo scriviFavouriteFile per aggiornare il file CSV dei preferiti.
     * @param username lo username dell'utente
     * @param ristorante l'id del ristorante da rimuovere
     */
    public void rimuoviPreferitoUtente(String username, String ristorante) {
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                String currentFavs = fav[1];
                if (currentFavs == null || currentFavs.isEmpty()) {
                } else {
                    String[] preferiti=fav[1].split(",");
                    String[] appoggio=preferiti.clone();
                    String favorite="";
                    for (String string : appoggio) {
                        if(!string.equals(ristorante)){
                            favorite=favorite+string+",";
                        }
                    }
                    fav[1]=favorite;
                }
                scriviFavouriteFile();
                return;
            }
        }
    }
    /**
     * Metodo che rimuove un preferito all'utente con lo username specificato.<br>
     * Chiama il metodo scriviFavouriteFile per aggiornare il file CSV dei preferiti.
     * @param username lo username dell'utente
     * @param ristorante il ristorante da rimuovere
     */
    public void rimuoviPreferitoUtente(String username, String[] ristorante) {
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                String currentFavs = fav[1];
                String ristoranteString = ristorante[16]; 
                
                if (currentFavs == null || currentFavs.isEmpty()) {
                    
                } else {
                    String[] preferiti=fav[1].split(",");
                    String[] appoggio=preferiti.clone();
                    String favorite="";
                    for (String string : appoggio) {
                        
                        if(!string.equals(ristoranteString)){
                            if (favorite=="") {
                                favorite=string;
                            } else {
                                favorite=favorite+","+string;
                            }
                        }
                    }
                    fav[1]=favorite;
                }
                scriviFavouriteFile();
                return;
            }
        }
    }
}
