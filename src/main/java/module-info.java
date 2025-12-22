module com.mycompany.theknife {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.opencsv;
    
    opens com.mycompany.theknife to javafx.fxml;
    exports com.mycompany.theknife;
    
}
