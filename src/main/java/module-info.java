module com.actividades.rapibookgit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.actividades.rapibookgit to javafx.fxml;
    exports com.actividades.rapibookgit;
}