package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.PrestamoDAO;
import com.actividades.rapibookgit.model.Prestamo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class PantallaVerPrestamos {


    @FXML
    private ListView listaPrestamos;
    @FXML
    private Label labelPrestamo;
    private ObservableList<Prestamo> list = FXCollections.observableArrayList();;

    public void initialize() {
        List<Prestamo> prestamos = UsuarioActualController.getInstance().getUsuario().getList();
        list.setAll(prestamos);
        listaPrestamos.setItems(list);
        if (list.isEmpty()){
            labelPrestamo.setText("Aqui apareceran tus prestamos");
        }else {
            listaPrestamos.setStyle("-fx-font-size: 16px;");
            labelPrestamo.setText("");
        }
    }


}
