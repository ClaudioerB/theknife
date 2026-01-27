package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

/**
 * @author TheKnifeTeam
 * 
 * HomeNotLoggedController rappresenta il controller per la scena HomeNotLogged.<br>
 * Si occupa di gestire l'interfaccia utente della scena HomeNotLogged prima dell'accesso.<br>
 * Le sue funzioni includono la visualizzazione nel dettaglio dei ristoranti con l'utilizzo di filtri.
 * Inoltre permette di accedere al profilo dell'utente non loggato che rappresenta il login e la registrazione.<br>
 * 
 * @version 1.0
 */
public class HomeNotLoggedController {

    @FXML
    private ImageView loginImageView;

    @FXML
    private ImageView knifeImageView;

    @FXML
    private org.controlsfx.control.Rating ratingFilter;

    @FXML
    private javafx.scene.control.MenuButton priceFilterComboBox;

    @FXML
    private javafx.scene.control.MenuButton deliveryFilterComboBox;

    @FXML
    private javafx.scene.control.MenuButton prenotationFilterComboBox;

    @FXML
    private javafx.scene.control.MenuButton cucineFilterComboBox;

    @FXML
    private javafx.scene.control.MenuButton cittaFilterComboBox;

    @FXML
    private javafx.scene.control.MenuButton statiFilterComboBox;

    @FXML
    private  javafx.scene.control.CheckMenuItem price1CheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem price2CheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem price3CheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem price4CheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem deliveryTrueCheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem deliveryFalseCheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem prenotationTrueCheckMenuItem;

    @FXML
    private  javafx.scene.control.CheckMenuItem prenotationFalseCheckMenuItem;

    @FXML
    private javafx.scene.control.TextField searchTextField;

    @FXML
    private javafx.scene.control.Button searchButton;

    private GestoreDataset gestoreDataset;

    @FXML
    private ListView<String> listViewRestaurants;

    @FXML 
    private javafx.scene.control.Button resetButton;
    
    @FXML
    private javafx.scene.control.Button viewButton;

    private ArrayList<String[]> filteredList;
    

    @FXML 
    private javafx.scene.control.RadioMenuItem tutteItem;

    @FXML 
    private javafx.scene.control.RadioMenuItem tutteItemCitta;

    @FXML 
    private javafx.scene.control.RadioMenuItem tutteItemStato;

    private String selectStatoItem;
    private ArrayList <String> dataStato;
    private ArrayList<String> dataCitta;
    private String selectedCittaItem = null;

    /**
     * Costruttore della scena HomeNotLoggedController.<br>
     * Inizializza il gestoreDataset e la filteredList per la visualizzazione dei ristoranti.<br>
     */
    public HomeNotLoggedController() {
        gestoreDataset = GestoreDataset.getGestoreDataset();
        filteredList = gestoreDataset.getDataSet();
    }
    private void setSelectedStato(String stato) {
        selectStatoItem = (stato == null || stato.trim().isEmpty()) ? "Tutti gli stati" : stato.trim();

        boolean tuttiGliStati = selectStatoItem.equalsIgnoreCase("Tutti gli stati");

        if (tuttiGliStati) {
            dataCitta = new ArrayList<>();
            cittaFilterComboBox.setDisable(true);
            selectedCittaItem = null;
        } else {
            dataCitta = gestoreDataset.getCittaByStato(selectStatoItem);
            if (dataCitta == null) dataCitta = new ArrayList<>();
            popolaMenuCittaConRadio();
            
            cittaFilterComboBox.setVisible(true);
            cittaFilterComboBox.setDisable(false);
            
            selectedCittaItem = null;
        }
    }



    private String getSelectStato() {
        return selectStatoItem;
    }

    /**
     * Metodo FXML che inizializza la scena HomeNotLoggedController.<br>
     * Inizializza le immagini e i filtri per la visualizzazione dei ristoranti.<br>
     */
    @FXML
    private void initialize() {
        filteredList = gestoreDataset.getDataSet();
        String path = System.getProperty("user.dir")
								+ "/src/main/java/com/mycompany/theknife/data/user.png"; 
        System.out.println("Path: " + path);
        java.io.File f = new java.io.File(path);
        if (f.exists()) {       
            System.out.println("Esiste? " + f.exists());
            loginImageView.setImage(
                    new javafx.scene.image.Image(f.toURI().toString())
            );
            loginImageView.setVisible(true);
            System.out.println("fcgcfgcfgcgfcfgcfg");
        }
        else{
            System.out.println("fcgcfgcfgcgfcfgcfg");
            path = System.getProperty("user.dir")
                    + "/../src/main/java/com/mycompany/theknife/data/user.png";
                    System.out.println("Path: " + path);
            java.io.File newFile = new java.io.File(path);
            if (newFile.exists()) {
                System.out.println("Esiste? " + f.exists());
                loginImageView.setImage(
                        new javafx.scene.image.Image(newFile.toURI().toString())
                );
                loginImageView.setVisible(true);
            }
        }

        System.out.println("Esiste? " + f.exists());

        dataStato = GestoreDataset.getDatasetStati();
        
        
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";   
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
            knifeImageView.setVisible(true);
        }else{
            knifePath=System.getProperty("user.dir")+ "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png"; 
            knifeFile = new java.io.File(knifePath);
            if (knifeFile.exists()) {       
                knifeImageView.setImage(
                        new javafx.scene.image.Image(knifeFile.toURI().toString())
                );
                
                knifeImageView.setVisible(true); 
            }
        }
        
        setSelectedStato("Tutti gli stati");
        ratingFilter.setRating(0);
        filteredList = gestoreDataset.getDataSet();
        popolaMenuCucineConRadio();
        //popolaMenuCittaConRadio();
        popolaMenuStatiConRadio();
        
        //cittaFilterComboBox.setVisible(false);
        //cittaFilterComboBox.setDisable(true);
        
        setFiltersTrue();
        fillListView(filteredList);
    }
    private void updateCittaVisibility() {
    boolean mostraCitta = (selectStatoItem != null)
            && !selectStatoItem.trim().equalsIgnoreCase("Tutti gli stati")
            && !selectStatoItem.trim().isEmpty();

    cittaFilterComboBox.setVisible(mostraCitta);
    cittaFilterComboBox.setDisable(!mostraCitta);
}

    /**
     * Metodo FXML che visualizza nel dettaglio un ristorante selezionato.<br>
     * Utilizzato per aprire la scena ViewRistorante usando il metodo setRoot della class App.<br>
     * @throws IOException 
     */
    @FXML
    private void visulizzaRistoranteButtonAction() throws IOException {
        String selectedItem = listViewRestaurants.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.startsWith("Nessun ristorante trovato")) {
            // Nessun elemento selezionato o messaggio di nessun ristorante trovato
            return;
        }
        String idRistorante = selectedItem.split(" - ")[0].replace("Ristorante N: ", "").trim();
        ControllerViewRistorante.getInstance(GestoreRicerche.getGestoreRicerche().trovaRistorantiID(idRistorante), false);
        App.setRoot("ViewRistorante");
    }

    /**
     * Metodo che popola il menu per la selezione delle cucine.<br>
     * Chiama il metodo checkFilteredList per la visualizzazione dei ristoranti.<br>
     */
    private void popolaMenuCucineConRadio() {
        ArrayList<String> tipiCucina = new ArrayList<>();
        boolean checkfirst = true;
        for (String row : GestoreDataset.getDataSetCucina()) {
            if (checkfirst) {
                checkfirst = false;
                //tipiCucina.add("Tutte le cucine");
                continue;
            }
            String tipoCucina = row;
            if (!tipiCucina.contains(tipoCucina)) {
                tipiCucina.add(tipoCucina);
            }
        }
        cucineFilterComboBox.getItems().clear();
        javafx.scene.control.ToggleGroup toggleGroup = new javafx.scene.control.ToggleGroup();
        tutteItem = new javafx.scene.control.RadioMenuItem("Tutte le cucine");
        tutteItem.setToggleGroup(toggleGroup);
        tutteItem.setSelected(true);
        tutteItem.setId("tutteCucine");
        tutteItem.setOnAction(e -> {
            try {
                checkFilteredList();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cucineFilterComboBox.getItems().add(tutteItem);
        cucineFilterComboBox.getItems().add(new javafx.scene.control.SeparatorMenuItem());
        for (String tipo : tipiCucina) {
            javafx.scene.control.RadioMenuItem radioItem = new javafx.scene.control.RadioMenuItem(tipo);
            radioItem.setToggleGroup(toggleGroup);
            radioItem.setOnAction(e -> {
                try {
                    checkFilteredList();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            cucineFilterComboBox.getItems().add(radioItem);
        }
    }
    private void popolaMenuCittaConRadio() {
    cittaFilterComboBox.getItems().clear();

    if (dataCitta == null) dataCitta = new ArrayList<>();

    // Rimuovi duplicati
    java.util.Set<String> setCitta = new java.util.LinkedHashSet<>();
    for (String s : dataCitta) {
        if (s != null && !s.trim().isEmpty()) {
            setCitta.add(s.trim());
        }
    }

    // "Tutte le città" -> nessun filtro città (ma mantiene filtro stato!)
    javafx.scene.control.MenuItem tutte = new javafx.scene.control.MenuItem("Tutte le città");
    tutte.setOnAction(e -> {
        selectedCittaItem = null; // Nessun filtro città
        try { 
            checkFilteredList(); 
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        }
    });
    cittaFilterComboBox.getItems().add(tutte);
    cittaFilterComboBox.getItems().add(new javafx.scene.control.SeparatorMenuItem());

    // Aggiungi ogni città
    for (String citta : setCitta) {
        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(citta);
        item.setOnAction(e -> {
            selectedCittaItem = citta.trim();
            //cittaFilterComboBox.setText(selectedCittaItem);
            try { 
                checkFilteredList(); 
            } catch (IOException ex) { 
                ex.printStackTrace(); 
            }
        });
        cittaFilterComboBox.getItems().add(item);
    }
}



    private void popolaMenuStatiConRadio() {
        setSelectedStato("Tutti gli stati");
        ArrayList<String> tipiStato = new ArrayList<>();
        boolean checkfirst = true;
        for (String row : dataStato) {
            if (checkfirst) {
                checkfirst = false;
                //tipiCucina.add("Tutte le cucine");
                continue;
            }
            String tipoStato = row;
            if (!tipiStato.contains(tipoStato)) {
                tipiStato.add(tipoStato);
            }
        }
        statiFilterComboBox.getItems().clear();
        javafx.scene.control.ToggleGroup toggleGroup = new javafx.scene.control.ToggleGroup();
        tutteItemStato = new javafx.scene.control.RadioMenuItem("Tutti gli stati");
        tutteItemStato.setToggleGroup(toggleGroup);
        tutteItemStato.setSelected(true);
        tutteItemStato.setId("tuttiStati");
        tutteItemStato.setOnAction(e -> {
            try {
                setSelectedStato("Tutti gli stati"); 
                //popolaMenuCittaConRadio();
                checkFilteredList();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        statiFilterComboBox.getItems().add(tutteItemStato);
        statiFilterComboBox.getItems().add(new javafx.scene.control.SeparatorMenuItem());
        for (String tipo : tipiStato) {
    javafx.scene.control.RadioMenuItem radioItem = new javafx.scene.control.RadioMenuItem(tipo);
    radioItem.setToggleGroup(toggleGroup);
    radioItem.setOnAction(e -> {
        try {
            // aggiorna la lista delle città per lo stato selezionato
            setSelectedStato(radioItem.getText());
            //popolaMenuCittaConRadio();
            checkFilteredList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
    statiFilterComboBox.getItems().add(radioItem);
}

    }
    /**
     * Metodo che aggiunge dalla lista dei ristoranti la cucina selezionata.<br>
     * Eliminando tutte le altre cucine.<br>
     * 
     * @param selectedCucina la cucina selezionata
     */
    private void removeCucina(String selectedCucina) {
        ArrayList<String[]> tempList = new ArrayList<>();
        selectedCucina = selectedCucina.trim();
        boolean checkfirst = true;
        for (String[] row : filteredList) {
            if (checkfirst) {
                checkfirst = false;
                tempList.add(row);
                continue;
            }
            boolean cucinaTrovata = false;
            if (row[5].contains(",")) {
                String[] tipiCucina = row[5].split(",");
                for (String tipoCucina : tipiCucina) {
                    String cucinaPulita = tipoCucina.trim().toLowerCase();
                    if (cucinaPulita.equals(selectedCucina)) {
                        cucinaTrovata = true;
                        break;
                    }
                }
            } else {
                String cucinaPulita = row[5].trim().toLowerCase();
                if (cucinaPulita.equals(selectedCucina)) {
                    cucinaTrovata = true;
                }
            }
            if (cucinaTrovata) {
                tempList.add(row);
            }
        }
        filteredList = tempList;
    }
    private void removeCitta(String selectedCitta) {
    ArrayList<String[]> tempList = new ArrayList<>();
    String target = selectedCitta.trim().toLowerCase();
    
    boolean checkfirst = true;
    for (String[] row : filteredList) {
        if (checkfirst) {
            checkfirst = false;
            tempList.add(row); // Mantieni header
            continue;
        }
        
        if (row[3].trim().toLowerCase().equals(target)) {
            tempList.add(row);
        }
    }
    filteredList = tempList;
}
    private void removeStato(String selectedStato) {
    ArrayList<String[]> tempList = new ArrayList<>();
    String target = selectedStato.trim().toLowerCase();

    boolean checkfirst = true;
    for (String[] row : filteredList) {
        if (checkfirst) {
            checkfirst = false;
            tempList.add(row); // Mantieni header
            continue;
        }
        
        if (row[2].trim().toLowerCase().equals(target)) {
            tempList.add(row);
        }
    }
    filteredList = tempList;
}

    /**
     * Metodo che ricerca i ristoranti in base alla cucina selezionata.<br>
     * Chiama il metodo removeCucina per la rimozione della cucina selezionata se non è "Tutte le cucine".<br>
     */
    private void searchingButtonActionTipiCucine() {
        String selectedCucina = "Tutte le cucine";
        for (javafx.scene.control.MenuItem item : cucineFilterComboBox.getItems()) {
            if (item instanceof javafx.scene.control.SeparatorMenuItem) {
                continue;  // Salta i separatori
            }
            if (item instanceof javafx.scene.control.RadioMenuItem) {
                javafx.scene.control.RadioMenuItem radioItem = (javafx.scene.control.RadioMenuItem) item;
                if (radioItem.isSelected()) {
                    selectedCucina = radioItem.getText();
                    break;
                }
            }
        }
        if (selectedCucina.equals("Tutte le cucine")) {
            return;
        } else {
            removeCucina(selectedCucina.toLowerCase());
        }
    }
    private void searchingButtonActionCitta() {
    if (selectedCittaItem == null || selectedCittaItem.trim().isEmpty()) {
        return; // Nessun filtro città
    }
    
    // Filtra per città
    removeCitta(selectedCittaItem.toLowerCase());
}

    private void searchingButtonActionStati() {
    String selectedStato = getSelectStato(); // Usa il getter!
    
    if (selectedStato == null || selectedStato.equalsIgnoreCase("Tutti gli stati")) {
        return; // Nessun filtro stato
    }
    
    // Filtra solo per stato
    removeStato(selectedStato.toLowerCase());
}

    /**
     * Metodo che imposta tutti i filtri a true.<br>
     */
    private void setFiltersTrue() {
        price1CheckMenuItem.setSelected(true);
        price2CheckMenuItem.setSelected(true);
        price3CheckMenuItem.setSelected(true);
        price4CheckMenuItem.setSelected(true);
        deliveryTrueCheckMenuItem.setSelected(true);
        deliveryFalseCheckMenuItem.setSelected(true);
        prenotationTrueCheckMenuItem.setSelected(true);
        prenotationFalseCheckMenuItem.setSelected(true);
        //tutteItem.setSelected(true);
        if (tutteItem != null) {
            tutteItem.setSelected(true);
        }
        //tutteCucine.setSelected(true);
    }

    /**
     * Metodo FXML che cambia l'immagine del login.<br>
     */
    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir") + "/src/main/java/com/mycompany/theknife/data/user_1.png"; 
        java.io.File newFile = new java.io.File(newPath);
        if (newFile.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(newFile.toURI().toString())
            );
        }
        else{
            newPath = System.getProperty("user.dir") + "/../src/main/java/com/mycompany/theknife/data/user_1.png"; 
            newFile = new java.io.File(newPath);
            if (newFile.exists()) {
                loginImageView.setImage(
                        new javafx.scene.image.Image(newFile.toURI().toString())
                );
            }
        }
    }

    /**
     * Metodo che riempie la listView con i ristoranti filtrati.<br>
     * Inserisce i ristoranti se la lista non è vuota.<br>
     * <ul>
     * <li>Imposta i valori di consegna e prenotazione.</li>
     * <li>Imposta la valutazione usando il metodo calcStelle.</li>
     * </ul>
     * @param list la lista filtrata dei ristoranti
     */
    private void fillListView(ArrayList<String[]> list) {
        listViewRestaurants.getItems().clear();
        boolean checkfirst = true;
        String deliveryValue, prenotationValue;
        for (String[] row : list) {
            if (row[13].trim().equals("Rating")) {
                continue;
            }else {
                deliveryValue = setDeliveryOrPrenotationValue(row[14]);
                prenotationValue = setDeliveryOrPrenotationValue(row[15]);
                String valutazione = String.valueOf(gestoreDataset.calcStelle(row[13]));
                listViewRestaurants.getItems().add("Ristorante N: "+row[16]+" - Nome: "+row[0] + " - Stato: " + row[2] + " - Città: " + row[3]+ " - Prezzo:" + row[4] + " - Tipo: " + row[5] + " - Consegna: " + deliveryValue + " - Prenotazione: " + prenotationValue + " - Valutazione: " + valutazione);
            }
        }
        if (list.isEmpty() || (list.size() == 1 && checkfirst == false)) {
            listViewRestaurants.getItems().add("Nessun ristorante trovato con i filtri selezionati.");
            //listViewRestaurants.refresh();
        }
        listViewRestaurants.refresh();
    }

    /**
     * Metodo che imposta il valore di consegna o prenotazione.<br>
     * @param value valore da impostare
     * @return il valore impostato
     */
    private String setDeliveryOrPrenotationValue(String value) {
        if (value.equals("1")) {
            return "Sì";
        } else if (value.equals("0")) {
            return "No";
        }
        return "N/A";
    }

    /**
     * Metodo che rimuove i prezzi dai filtri non selezionati.<br>
     * Chiama il metodo removePrice per rimuovere i prezzi dai filtri.<br>
     */
    private void pricingFilterComboBoxAction() {
        if (!price1CheckMenuItem.isSelected()) {
            removePrice(1);
        }
        if (!price2CheckMenuItem.isSelected()) {
            removePrice(2);
        }
        if (!price3CheckMenuItem.isSelected()) {
            removePrice(3);
        }
        if (!price4CheckMenuItem.isSelected()) {
            removePrice(4);
        }
    }

    /**
     * Metodo che rimuove i prezzi dai filtri non selezionati.<br>
     * @param num il numero di cifre del prezzo
     */
    private void removePrice(int num) {
        ArrayList<String[]> tempList = new ArrayList<>(filteredList);
        for (String[] row : filteredList) {
            String prezzo = row[4];
            if (prezzo.length() == num) {
                tempList.remove(row);
            }
        }
        filteredList = tempList;
    }

    /**
     * Metodo che rimuove i tag dai filtri non selezionati.<br>
     * @param value il valore del parametro
     * @param id l'id del parametro
     */
    private void removeTag(String value, int id) {
        ArrayList<String[]> tempList = new ArrayList<>(filteredList);
        for (String[] row : filteredList) {
            String code = row[id];
            if (code.contains(value)) {
                tempList.remove(row);
            }
        }
        filteredList = tempList;
    }

    /**
     * Metodo FXML che rimuove i ristoranti che non soddisfano il criterio di ricerca testuale.<br>
     * 
     */
    @FXML 
    private void searchingButtonAction() {
        String searchText = searchTextField.getText().toLowerCase();
        listViewRestaurants.getItems().clear();
        boolean checkfirst = true;
        ArrayList<String[]> tempList = new ArrayList<>();
        ArrayList<String[]> tempList2 = new ArrayList<>(filteredList);
        for (String[] row : filteredList) {
            if (checkfirst) {
                checkfirst = false;
            } else {
                String nome = row[0].toLowerCase();
                String indirizzo = row[1].toLowerCase();
                String stato = row[2].toLowerCase();
                String citta = row[3].toLowerCase();
                String prezzo = row[4].toLowerCase();
                String tipo = row[5].toLowerCase();
                String lon = row[6].toLowerCase();
                String lat = row[7].toLowerCase();
                String phone = row[8].toLowerCase();
                String url = row[9].toLowerCase();
                String award = row[10].toLowerCase();
                String servizi = row[11].toLowerCase();
                String descrizione = row[12].toLowerCase();

                if (!(nome.contains(searchText) || stato.contains(searchText) ||
                    citta.contains(searchText) || prezzo.contains(searchText) ||
                    indirizzo.contains(searchText) || lon.contains(searchText) ||
                    lat.contains(searchText) || phone.contains(searchText) ||
                    url.contains(searchText) || award.contains(searchText) ||
                    servizi.contains(searchText) || descrizione.contains(searchText) ||
                    tipo.contains(searchText))) {
                        //tempList.add(row);
                        tempList2.remove(row);
                        //listViewRestaurants.getItems().add("Nome: " + row[0] + " - Stato: " + row[2] + " -Città: " + row[3] + " -Prezzo:" + row[4] + " -Tipo: " + row[5]);
                }
            }
        }
        filteredList = tempList2;
    }

    /**
     * Metodo che rimuove i ristoranti che non soddisfano il criterio di consegna.<br>
     * Chiama il metodo removeTag per rimuovere i tag dai filtri non selezionati.<br>
     * @throws IOException 
     */
    private void deliveringFilterComboBoxAction2() throws IOException {
        int tag = 14;
        if (!deliveryTrueCheckMenuItem.isSelected()) {
            removeTag("1", tag);
        }
        if(!deliveryFalseCheckMenuItem.isSelected()){
            removeTag("0", tag);
        }
    }

    /**
     * Metodo che rimuove i ristoranti che non soddisfano il criterio di prenotazione.<br>
     * Chiama il metodo removeTag per rimuovere i tag dai filtri non selezionati.<br>
     * @throws IOException
     */
    private void bookingFilterComboBoxAction2() throws IOException {
        int tag = 15;
        if (!prenotationTrueCheckMenuItem.isSelected()) {
            removeTag("1", tag);
        }
        if(!prenotationFalseCheckMenuItem.isSelected()){
            removeTag("0", tag);
        }
    }

    /**
     * Metodo che rimuove i ristoranti che non soddisfano il criterio di valutazione.<br>
     * Chiama il metodo calcStelle per calcolare la valutazione.<br>
     */
    private void starAction() {
        int tag = 13;
        double rating = ratingFilter.getRating();
        //System.out.println(rating);
        if (rating  == 0) {
            return;
        }
        else {
            ArrayList<String[]> tempList = new ArrayList<>(filteredList);
            for (int i = tempList.size() - 1; i >= 0; i--) {
                String[] row = tempList.get(i);
                if (i == 0) {
                    continue;
                }
                
                double restaurantRating;
                try {
                    restaurantRating = gestoreDataset.calcStelle(row[tag]);
                } catch (NumberFormatException e) {
                    tempList.remove(i);
                    continue;
                }
                if (restaurantRating < rating) {
                    tempList.remove(i);
                }
            }
            filteredList = tempList;
        }
    }   

    /**
     * Metodo FXML che esegue i filtri e stampa i ristoranti filtrati.<br>
     * 
     * @throws IOException
     */
    @FXML
private void checkFilteredList() throws IOException {
    filteredList = gestoreDataset.getDataSet();
    
    starAction();
    searchingButtonActionTipiCucine();
    searchingButtonActionStati();      // Filtra per stato (se selezionato)
    searchingButtonActionCitta();      // Filtra per città (se selezionata)
    deliveringFilterComboBoxAction2();
    bookingFilterComboBoxAction2();
    pricingFilterComboBoxAction();
    searchingButtonAction();
    
    // RIMOSSO: tutteItemStato.setSelected(true); 
    // NON resettare lo stato qui!
    
    fillListView(filteredList);
}

    /**
     * Metodo FXML che resetta tutti i filtri e stampa tutti i ristoranti completi.<br>
     */
    @FXML 
private void resetButtonAction() {
    // Reset tutti i filtri
    setFiltersTrue();
    ratingFilter.setRating(0);
    searchTextField.clear();
    
    // Reset selezione stato e città
    if (tutteItemStato != null) {
        tutteItemStato.setSelected(true);
    }
    setSelectedStato("Tutti gli stati"); // Nasconde menu città
    selectedCittaItem = null;
    
    // Mostra tutti i ristoranti
    filteredList = gestoreDataset.getDataSet();
    fillListView(filteredList);
}


    /**
     * Metodo FXML che permette di passare alla scena di login.<br>
     * 
     * @throws IOException
     */
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}