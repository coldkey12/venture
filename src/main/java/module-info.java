module com.example.aviatickets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.aviatickets to javafx.fxml;
    exports com.example.aviatickets;
}