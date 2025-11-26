module com.actividades.rapibookgit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.xml.bind;
    requires jbcrypt;
    requires java.desktop;


    opens com.actividades.rapibookgit to javafx.fxml;
    opens com.actividades.rapibookgit.controller to javafx.fxml;
    opens com.actividades.rapibookgit.model to jakarta.xml.bind;
    opens com.actividades.rapibookgit.baseDatos to jakarta.xml.bind;
    exports com.actividades.rapibookgit;
    exports com.actividades.rapibookgit.controller;
}