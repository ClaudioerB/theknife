module com.mycompany.theknife {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.theknife to javafx.fxml;
    exports com.mycompany.theknife;
    requires com.opencsv;
}
