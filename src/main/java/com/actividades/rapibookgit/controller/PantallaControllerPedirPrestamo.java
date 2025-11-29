package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.PrestamoDAO;
import com.actividades.rapibookgit.model.Libro;
import com.actividades.rapibookgit.model.Prestamo;
import com.actividades.rapibookgit.model.Usuario;
import javafx.event.ActionEvent;
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
    public ImageView imagenPortada;
    public Label Titulo;
    public Label anno;
    public Label editorial;
    public Label precio;
    public DatePicker fechaPrestamo;
    private Libro libroSeleccionado;

    public void initialize(){


    }
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
