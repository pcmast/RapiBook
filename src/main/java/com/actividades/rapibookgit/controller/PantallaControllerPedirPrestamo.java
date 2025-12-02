package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.PrestamoDAO;
import com.actividades.rapibookgit.model.Libro;
import com.actividades.rapibookgit.model.Prestamo;
import com.actividades.rapibookgit.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaControllerPedirPrestamo {
    @FXML
    private ImageView imagenPortada;
    @FXML
    private Label Titulo;
    @FXML
    private Label anno;
    @FXML
    private Label editorial;
    @FXML
    private Label precio;
    @FXML
    private DatePicker fechaPrestamo;
    private Libro libroSeleccionado;


    public void initialize(){


    }

    /*
    * Metodo que asigna al libro de esta clase los datos seleccionados desde otro metodo
    *
    * */
    public void setLibro(Libro libro) {
        this.libroSeleccionado = libro;

        // Actualizar la UI con los datos del libro
        Titulo.setText(libro.getTitulo());
        anno.setText(String.valueOf(libro.getAno()));
        editorial.setText(libro.getEditorial());
        precio.setText("$" + libro.getPrecio());

        Image img = new Image("file:" + libro.getPortada(), 100, 150, true, true);
        imagenPortada.setImage(img);
    }


    /*
    * Metodo que registra un prestamo con el usuario y el libro que se selecciono desde otro controlador y la fecha
    * tanto la fecha del prestamo como la fecha de devolucion del mismo
    * */
    public void pedirPrestamo(ActionEvent event) {

        if (fechaPrestamo.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione una fecha de devolución.");
            alerta.showAndWait();
            return;
        }

        UsuarioActualController usuarioActualController = UsuarioActualController.getInstance();
        Usuario usuario = usuarioActualController.getUsuario();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaPrestamoStr = LocalDate.now().format(formatter);
        String fechaDevolucionStr = fechaPrestamo.getValue().format(formatter);


        Prestamo prestamo = new Prestamo(usuario, libroSeleccionado, fechaPrestamoStr, fechaDevolucionStr);

        PrestamoDAO.insertarPrestamo(prestamo);
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Préstamo registrado");
        exito.setHeaderText(null);
        exito.setContentText("El préstamo del libro \"" + libroSeleccionado.getTitulo() + "\" se ha registrado correctamente.");
        exito.showAndWait();
    }

}
