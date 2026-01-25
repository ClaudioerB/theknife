package com.mycompany.theknife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class GestoreUtenti {
   
    private ArrayList<Utente> utenti;    
    private String filePath;
    private String fileUserPath;
    private String personeRistorantiPath;
    private static GestoreUtenti gestoreUtenti;

    private static String pathFavourite;
    private static ArrayList<String[]> dataSetFavourite;

    private static ArrayList<String[]> personeRistoranti;

    private GestoreUtenti() {
        filePath = System.getProperty("user.dir")+"/src/main/resources/Users/users.csv"; 
        fileUserPath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\datasetUtenti.CSV";
        pathFavourite = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\favourite.csv";
        personeRistorantiPath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\personeRistoranti.CSV";

        this.utenti = new ArrayList<Utente>();
        this.dataSetFavourite= new ArrayList<String[]>();
        personeRistoranti= new ArrayList<String[]>();
        gestoreUtenti = this;
        //inserimentoDati();
        inserimentoNewDati();

        //createFavouriteDataSet();
        inserimentoFavouriteDati();
        aggiungiRigheFavourite();
        scriviFavouriteFile();
        //inserimentoFavouriteDati();

        //createPersoneRistorantiDataSet();
        inserimentoPersoneRistorantiDati();
        aggiungiRighePersoneRistoranti();
        scriviPersoneRistorantiFile();
    }
    public static GestoreUtenti getGestoreUtenti() {
        if(gestoreUtenti == null) {
            gestoreUtenti = new GestoreUtenti();
        }
        return gestoreUtenti;
    }

    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    public ArrayList<String[]> getFavourite() {
        return dataSetFavourite;
    }
    public ArrayList<String[]> getPersoneRistoranti() {
        return personeRistoranti;
    }

    public Utente getUtenteSingolo(int i) {
        return utenti.get(i);
    }

    public void setUtenteSingolo(Utente utente, int i) {
        this.utenti.set(i, utente);

    }
    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
        scriviNewFile();
    }
    public void rimuoviUtente(int i) {
        utenti.remove(i);
        scriviNewFile();
    }
    public void printUtenti() {
        for (Utente utente : utenti) {
            System.out.println(utente.getNome()+" "+utente.getCognome()+" "+utente.getUsername()+" "+utente.getPasswordHash()+" "+utente.getCittà()+" "+utente.getIndirizzo());
        }
    }

    /*public boolean checkFavourite(String username) {
        for (String[] fav : dataSetFavourite) {
            if (fav[1] != null && !fav[1].isEmpty()) {
                return true; // Esiste almeno un favorito
            }
        }
        return false; // Nessun favorito trovato
    }*/
    public String getFavouriteByUsername(String username) {
        for (String[] fav : dataSetFavourite) {
            if (fav[0].equals(username)) {
                return fav[1]; // Restituisce i preferiti dell'utente trovato
            }
        }
        return null; // Utente non trovato
    }
    public String getPersoneRistorantiByIdRistorante(String id) {
        for (String[] row : personeRistoranti) {
            if (row[0].equals(id)) {
                return row[1]; 
            }
        }
        return null; // Utente non trovato
    }
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
    /*public void addPersoneRistorantiByIdUtente(String idUtente, String idRistorante) {
        for (int i = 0; i < personeRistoranti.size(); i++)
            if (fav[1].equals(idUtente)) {
                if (!fav[0].isEmpty() || fav[0] != "") {
                    
                }
            }
        }
    }*/
    public void addNewPersoneRistoranti(String idU, String idR) {
        String[] rowNew = {idR,idU};
        //personeRistoranti.add(rowNew);
        
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
    public void addNewFavourite(String username, String newFav) {
        //addNewFavourite(utenteLoggato.getUsername(), ristoranteFav);
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
            // Aggiungi solo se non esiste
            if (!esisteGià) {
                dataSetFavourite.add(new String[]{username, ""});
            }
        }
        //System.out.println("Favourite caricato");
        scriviFavouriteFile();
    }
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
            // Aggiungi solo se non esiste
            if (!esisteGià) {
                dataSetFavourite.add(new String[]{id, ""});
            }
        }
        //System.out.println("Favourite caricato");
        scriviPersoneRistorantiFile();
    }

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

    private void scriviFavouriteFile() {
        //createFavouriteDataSet();
        try (CSVWriter writer = new CSVWriter(new FileWriter(pathFavourite),
        ';',       // separatore personalizzato
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
        //System.out.println("CSV scritto con successo!");
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void scriviPersoneRistorantiFile() {
        //createFavouriteDataSet();
        try (CSVWriter writer = new CSVWriter(new FileWriter(personeRistorantiPath),
        ';',       // separatore personalizzato
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
        //System.out.println("CSV scritto con successo!");
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void scriviNewFile() {
        
    
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileUserPath),
        ';',       // separatore personalizzato
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

    private void inserimentoNewDati() {
        String[] appoggio;
        int iRow = 0;
        String idAppoggio;
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

    private void inserimentoFavouriteDati() {
        int iRow = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFavourite))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Salta righe vuote
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] appoggio = line.split(";", -1);
                
                // Verifica lunghezza e aggiungi
                if (appoggio.length >= 2) {
                    // Caso normale: username;favourites
                    dataSetFavourite.add(new String[]{appoggio[0], appoggio[1]});
                } else if (appoggio.length == 1) {
                    // Caso con solo username (nessun favourite)
                    dataSetFavourite.add(new String[]{appoggio[0], ""});
                } else {
                    // Riga malformata, salta
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

    private void inserimentoPersoneRistorantiDati() {
        int iRow = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(personeRistorantiPath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Salta righe vuote
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] appoggio = line.split(";", -1);
                
                // Verifica lunghezza e aggiungi
                if (appoggio.length >= 2) {
                    // Caso normale: username;favourites
                    personeRistoranti.add(new String[]{appoggio[0], appoggio[1]});
                } else if (appoggio.length == 1) {
                    // Caso con solo username (nessun favourite)
                    personeRistoranti.add(new String[]{appoggio[0], ""});
                } else {
                    // Riga malformata, salta
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

    public boolean verificaCredenziali(String username, String password) {
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username) && utente.getPasswordHash().equals(password)) {
                return true; // Credenziali valide
            }
            /*if (utente.getUsername() != null) {
                System.out.println("Username scorretto");
            }*/
        }
        /*for (String[] user : users) {
            //System.out.println(user[2]);
            if (user[1].equals(username) && user[2].equals(password)) {
                return true; // Credenziali valide
            }
        }*/
        return false; // Credenziali non valide
    }

    public void aggiornaUtente(Utente utente) {
    for (int i = 0; i < utenti.size(); i++) {
        if (utenti.get(i).getUsername().equals(utente.getUsername())) {
            utenti.set(i, utente);
            scriviNewFile(); 
            return;
        }
    }
    }

public boolean controlloPassword(String password) {
        // Implementa qui la logica per controllare la validità della password
        // Ad esempio, puoi verificare la lunghezza minima, la presenza di caratteri speciali, ecc.
        if (password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*")) {
            return true; // Password valida
        } else {
            return false; // Password non valida
        }
    }

    public Utente getUtenteByUsername(String username) {
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username)) {
                return utente; // Restituisce l'utente trovato
            }
        }
        return null; // Utente non trovato
    }

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
        // Aggiungi il nuovo utente alla lista
        aggiungiUtente(nuovoUtente);
        return 0; // Utente creato con successo
    }
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
    public void printPreferitiUtente(){
        Gestore gestore=Gestore.getGestore();
        for (String[] string : gestore.getUtenteLoggato().getPreferiti()) {
            System.out.println(string[1]);
        }
    }
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
                            //favorite=favorite+string;
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
