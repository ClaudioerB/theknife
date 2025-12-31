module com.mycompany.theknife {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.opencsv;
    
    opens com.mycompany.theknife to javafx.fxml;
    opens com.mycompany.theknife.controllers to javafx.fxml;

    exports com.mycompany.theknife;
    
}
