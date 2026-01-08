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

    private ArrayList<String[]> filteredList;

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
        }
        String knifePath = System.getProperty("user.dir")
                + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
        }
        setFiltersTrue();
        fillListView(gestoreDataset.getDataSet());
        
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
    }

    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir")
                + "\\src\\main\\java\\com\\mycompany\\theknife\\data\\user_1.png"; 
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
        for (String[] row : list) {
            if (checkfirst) {
                checkfirst = false;
            }else {
                listViewRestaurants.getItems().add("Nome: "+row[0] + " - Stato: " + row[2] + " -Città: " + row[3]+ " -Prezzo:" + row[4] + " -Tipo: " + row[5] );
                listViewRestaurants.refresh();
            }
        }       
    }

  
    private void priceFilterComboBoxAction() {
        GestoreRicerche gestoreRicerche = new GestoreRicerche();
        if (price1CheckMenuItem.isSelected()) {
            
        }
        else
        {
            filteredList.removeAll(gestoreRicerche.trovaRistorantiCosto("€"));
        }
        if (price2CheckMenuItem.isSelected()) {
            
        }
        else
        {
            filteredList.removeAll(gestoreRicerche.trovaRistorantiCosto("€€"));
        }
        if (price3CheckMenuItem.isSelected()) {
            
        }
        else
        {
            filteredList.removeAll(gestoreRicerche.trovaRistorantiCosto("€€€"));
        }
        if (price4CheckMenuItem.isSelected()) {
            
        }
        else
        {
            filteredList.removeAll(gestoreRicerche.trovaRistorantiCosto("€€€€"));
        }
        

    }

    
    private void searchButtonAction() {
        String searchText = searchTextField.getText().toLowerCase();
        listViewRestaurants.getItems().clear();
        boolean checkfirst = true;
        for (String[] row : gestoreDataset.getDataSet()) {
            if (checkfirst) {
                checkfirst = false;
            } else {
                String nome = row[0].toLowerCase();
                String stato = row[2].toLowerCase();
                String citta = row[3].toLowerCase();
                String prezzo = row[4].toLowerCase();
                String tipo = row[5].toLowerCase();

                if (nome.contains(searchText) || stato.contains(searchText) ||
                    citta.contains(searchText) || prezzo.contains(searchText) ||
                    tipo.contains(searchText)) {
                    
                    //listViewRestaurants.getItems().add("Nome: " + row[0] + " - Stato: " + row[2] + " -Città: " + row[3] + " -Prezzo:" + row[4] + " -Tipo: " + row[5]);
                }
                else {
                    filteredList.remove(row);
                }
            }
        }
    }
    
   
    private void deliveryFilterComboBoxAction() throws IOException {
        boolean yesSelected;
        boolean noSelected;
        if (deliveryTrueCheckMenuItem.isSelected()) {
            // Aggiungi filtro consegna disponibile
            yesSelected=true;
        } else {
            // Rimuovi filtro consegna disponibile
            yesSelected=false;
        }
        if(deliveryFalseCheckMenuItem.isSelected()){
                noSelected=true;
        } else {
                noSelected=false;
        }
        GestoreRicerche gestoreRicerche = new GestoreRicerche();
        fillListView(gestoreRicerche.trovaRistorantiDelivery(yesSelected,noSelected,filteredList));
    }

   
    private void ratingsAction() throws IOException {
        double rating = ratingFilter.getRating();
        GestoreRicerche gestoreRicerche = new GestoreRicerche();
        filteredList=gestoreRicerche.trovaRistorantiRating(rating, filteredList);
    }
    
 
    private void prenotationFilterComboBoxAction() throws IOException {
        boolean yesSelected;
        boolean noSelected;
        if (prenotationTrueCheckMenuItem.isSelected()) {
            // Aggiungi filtro prenotazione disponibile
            yesSelected=true;
            
        } else {
            // Rimuovi filtro prenotazione disponibile
            yesSelected=false;
        }
        if(prenotationFalseCheckMenuItem.isSelected()){
                noSelected=true;
        } else {
                noSelected=false;
        }
        GestoreRicerche gestoreRicerche = new GestoreRicerche();
        filteredList=gestoreRicerche.trovaRistorantiPrenotation(yesSelected,noSelected,filteredList);
    }

    private void cucineFilterComboBoxAction() {
        // Implementa il filtro per tipo di cucina
        
    }

    @FXML
    private void checkFilteredList() throws IOException {
        filteredList = gestoreDataset.getDataSet();
        prenotationFilterComboBoxAction();
        deliveryFilterComboBoxAction();
        priceFilterComboBoxAction();
        ratingsAction();
        cucineFilterComboBoxAction();
        searchButtonAction();
        fillListView(filteredList);
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}
