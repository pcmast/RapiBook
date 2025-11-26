package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.AutorDAO;
import com.actividades.rapibookgit.model.Autor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class PantallaControllerAutores {


    public ListView listaAutores;
    public ImageView reiniciarLista;
    public Button botonSeleccionar;
    public Button botonCrearAutor;
    private ObservableList<Autor> list;

    private ControllerAnnadirLibro controllerAnnadirLibro;

    public void setControllerAnnadirLibro(ControllerAnnadirLibro controller) {
        this.controllerAnnadirLibro = controller;
    }

    public void initialize() {
        list = FXCollections.observableArrayList();
        list.addAll(AutorDAO.todosLosAutores());
        listaAutores.setItems(list);
        botonSeleccionar.setDisable(true);
        botonSeleccionar.setVisible(false);
        File file = new File("images/reset.png");
        Image image = new Image(file.toURI().toString());
        reiniciarLista.setImage(image);
    }


    public void crearAutor(ActionEvent event) {
        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setTitle("Nuevo Autor");
        dialogNombre.setHeaderText("Introduce el nombre del autor");
        dialogNombre.setContentText("Nombre:");
        Optional<String> resultadoNombre = dialogNombre.showAndWait();

        resultadoNombre.ifPresent(nombre -> {
            if (!nombre.trim().isEmpty()) {
                TextInputDialog dialogBio = new TextInputDialog();
                dialogBio.setTitle("Biografía");
                dialogBio.setHeaderText("Introduce una biografía corta del autor");
                dialogBio.setContentText("Biografía:");
                Optional<String> resultadoBio = dialogBio.showAndWait();
                String biografia = resultadoBio.orElse("");

                AutorDAO.insertarAutor(nombre, biografia);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Autor creado");
                alert.setHeaderText(null);
                alert.setContentText("El autor '" + nombre + "' ha sido creado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo vacío");
                alert.setHeaderText(null);
                alert.setContentText("No se puede crear un autor sin nombre.");
                alert.showAndWait();
            }
        });
    }

    public void reiniciar(MouseEvent mouseEvent) {
        list.clear();
        list.addAll(AutorDAO.todosLosAutores());
        listaAutores.setItems(list);

    }
    public void inicializarBotones() {
        botonSeleccionar.setDisable(false);
        botonSeleccionar.setVisible(true);
        botonCrearAutor.setDisable(true);
        botonCrearAutor.setVisible(false);
    }

    public void SeleccionarAutor(ActionEvent event) {
        Autor autorSeleccionado = (Autor) listaAutores.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {


            if (controllerAnnadirLibro != null) {
                controllerAnnadirLibro.recibirAutorSeleccionado(autorSeleccionado);
            }

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
        }
    }

}
