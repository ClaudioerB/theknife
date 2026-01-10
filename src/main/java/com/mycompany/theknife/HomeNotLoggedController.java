package com.mycompany.theknife;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

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

    private ArrayList<String[]> filteredList;

    @FXML 
    private javafx.scene.control.RadioMenuItem tutteItem;

    public HomeNotLoggedController() {
        gestoreDataset = new GestoreDataset();
        filteredList = gestoreDataset.getDataSet();
        
    }

    @FXML
    private void initialize() {
        String path = System.getProperty("user.dir")
                + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\user.png";
        System.out.println("Path: " + path);
        java.io.File f = new java.io.File(path);

        System.out.println("Esiste? " + f.exists());

        if (f.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(f.toURI().toString())
            );
            loginImageView.setVisible(true);
        }
        else {
            System.out.println("File immagine non trovato: " + path);
        }
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
            knifeImageView.setVisible(true);
        }
        //gestoreDataset.createCucineDataSet();
        //gestoreDataset.aggiungiRigaCucina(path);
        //gestoreDataset.printDataSetCucina(2);

        ratingFilter.setRating(0);
        filteredList = gestoreDataset.getDataSet();
        popolaMenuCucineConRadio();

        setFiltersTrue();
        fillListView(filteredList);
    }

    private void popolaMenuCucineConRadio() {
        ArrayList<String> tipiCucina = new ArrayList<>();
        boolean checkfirst = true;
        //for (String row : gestoreDataset.getDataSetCucina()) {
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
        //System.out.println(tutteItem.getId());
        cucineFilterComboBox.getItems().add(tutteItem);

        cucineFilterComboBox.getItems().add(new javafx.scene.control.SeparatorMenuItem());
        //cucineFilterComboBox.getItems().clear();
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
        //System.out.println(cucineFilterComboBox.getItems().get(0).getText());
    }

    private void removeCucina(String selectedCucina) {
        ArrayList<String[]> tempList = new ArrayList<>(filteredList);
        for (String[] row : filteredList) {
            String tipoCucina = row[5].toLowerCase();
            if (!tipoCucina.equals(selectedCucina)) {
                tempList.remove(row);
            }
        }
        filteredList = tempList;
        /*
        filteredList.removeIf(row -> 
            row.length > 5 && !row[5].toLowerCase().equals(selectedCucina)
        ); */
    }

    private void searchingButtonActionTipiCucine() {
        //cucineFilterComboBox.getItems();
        String selectedCucina = "Tutte le cucine";
        
        for (javafx.scene.control.MenuItem item : cucineFilterComboBox.getItems()) {
            if (item instanceof javafx.scene.control.SeparatorMenuItem) {
                continue;  // Salta i separatori
            }
        
            if (item instanceof javafx.scene.control.RadioMenuItem) {
                javafx.scene.control.RadioMenuItem radioItem = (javafx.scene.control.RadioMenuItem) item;
                if (radioItem.isSelected()) {
                    //if (item.isSelected()) {
                selectedCucina = radioItem.getText();
                break;
                //System.out.println(selectedCucina);
                /*if (selectedCucina.equals("Tutte le cucine")) {
                    return;
                } else {
                    removeCucina(selectedCucina);
                }*/
                //removeCucina(selectedCucina);
            }
        }
        }

        if (selectedCucina.equals("Tutte le cucine")) {
            return;
        } else {
            removeCucina(selectedCucina.toLowerCase());
        }
    }

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

    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\user_1.png"; 
        java.io.File newFile = new java.io.File(newPath);
        if (newFile.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(newFile.toURI().toString())
            );
        }
    }

    private void fillListView(ArrayList<String[]> list) {
        listViewRestaurants.getItems().clear();
        boolean checkfirst = true;
        String deliveryValue, prenotationValue;
        for (String[] row : list) {
            if (checkfirst) {
                checkfirst = false;
            }else {
                deliveryValue = setDeliveryOrPrenotationValue(row[14]);
                prenotationValue = setDeliveryOrPrenotationValue(row[15]);

                listViewRestaurants.getItems().add("Nome: "+row[0] + " - Stato: " + row[2] + " - Città: " + row[3]+ " - Prezzo:" + row[4] + " - Tipo: " + row[5] + " - Consegna: " + deliveryValue + " - Prenotazione: " + prenotationValue + " - Valutazione: " + row[13]);
                listViewRestaurants.refresh();
            }
        }
        if (list.isEmpty() || (list.size() == 1 && checkfirst == false)) {
            listViewRestaurants.getItems().add("Nessun ristorante trovato con i filtri selezionati.");
            listViewRestaurants.refresh();
        }
    }

    private String setDeliveryOrPrenotationValue(String value) {
        if (value.equals("1")) {
            return "Sì";
        } else if (value.equals("0")) {
            return "No";
        }
        return "N/A";
    }

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
                String stato = row[2].toLowerCase();
                String citta = row[3].toLowerCase();
                String prezzo = row[4].toLowerCase();
                String tipo = row[5].toLowerCase();

                if (!(nome.contains(searchText) || stato.contains(searchText) ||
                    citta.contains(searchText) || prezzo.contains(searchText) ||
                    tipo.contains(searchText))) {
                        //tempList.add(row);
                        tempList2.remove(row);
                        //listViewRestaurants.getItems().add("Nome: " + row[0] + " - Stato: " + row[2] + " -Città: " + row[3] + " -Prezzo:" + row[4] + " -Tipo: " + row[5]);
                }
            }
        }
        filteredList = tempList2;
    }

    private void deliveringFilterComboBoxAction2() throws IOException {
        int tag = 14;
        if (!deliveryTrueCheckMenuItem.isSelected()) {
            removeTag("1", tag);
        }
        if(!deliveryFalseCheckMenuItem.isSelected()){
            removeTag("0", tag);
        }
    }

    private void bookingFilterComboBoxAction2() throws IOException {
        int tag = 15;
        if (!prenotationTrueCheckMenuItem.isSelected()) {
            removeTag("1", tag);
        }
        if(!prenotationFalseCheckMenuItem.isSelected()){
            removeTag("0", tag);
        }
    }

    private void starAction() {
        int tag = 13;
        double rating = ratingFilter.getRating();
        System.out.println(rating);

        if (rating  == 0) {
            // Non applicare alcun filtro
            return;
        }
        else {
            ArrayList<String[]> tempList = new ArrayList<>(filteredList);
            for (String[] row : filteredList) {
                double restaurantRating;
                try {
                    restaurantRating = Double.parseDouble(row[tag]);
                } catch (NumberFormatException e) {
                    // Se il valore non è un numero valido, rimuovi il ristorante
                    tempList.remove(row);
                    continue;
                }
                if (restaurantRating != rating) {
                    tempList.remove(row);
                }
                /*if (restaurantRating < rating) {
                    tempList.remove(row);
                } */
            }
            filteredList = tempList;
        }
    }    

    @FXML
    private void checkFilteredList() throws IOException {
        filteredList = gestoreDataset.getDataSet();
        //prenotationFilterComboBoxAction();
        starAction();
        searchingButtonActionTipiCucine();
        deliveringFilterComboBoxAction2();
        bookingFilterComboBoxAction2();
        pricingFilterComboBoxAction();
        searchingButtonAction();
        fillListView(filteredList);
    }


    @FXML 
    private void resetButtonAction() {
        setFiltersTrue();
        ratingFilter.setRating(0);
        searchTextField.clear();
        filteredList = gestoreDataset.getDataSet();
        fillListView(filteredList);
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}