package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.AutorDAO;
import com.actividades.rapibookgit.DAO.LibroAutorDAO;
import com.actividades.rapibookgit.model.Autor;
import com.actividades.rapibookgit.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PantallaControllerAutores {


    public ListView listaAutores;
    public ImageView reiniciarLista;
    public Button botonSeleccionar;
    public Button botonCrearAutor;
    public Button btnEliminarAutor;
    public Button botonSeleccionarDeUnLibro;
    public Button btnEliminarAutorDeUnLibro;
    private ObservableList<Autor> list;
    private ControllerAnnadirLibro controllerAnnadirLibro;
    private PantallaPrincipalAdministrador pantallaPrincipalAdministrador;
    private Libro libro;


    public void initialize() {
        list = FXCollections.observableArrayList();
        list.addAll(AutorDAO.todosLosAutores());
        listaAutores.setItems(list);
        botonSeleccionar.setDisable(true);
        botonSeleccionar.setVisible(false);
        botonSeleccionarDeUnLibro.setDisable(true);
        botonSeleccionarDeUnLibro.setVisible(false);
        btnEliminarAutorDeUnLibro.setDisable(true);
        btnEliminarAutorDeUnLibro.setVisible(false);
        File file = new File("images/reset.png");
        Image image = new Image(file.toURI().toString());
        reiniciarLista.setImage(image);
    }

    public void setControllerAnnadirLibro(ControllerAnnadirLibro controller) {
        this.controllerAnnadirLibro = controller;
    }

    public void listaAutoresParaAnnadirOEliminar(Libro libro){
        this.libro = libro;
        botonSeleccionar.setDisable(true);
        botonSeleccionar.setVisible(false);
        btnEliminarAutor.setVisible(false);
        btnEliminarAutor.setDisable(true);
        botonCrearAutor.setDisable(true);
        botonCrearAutor.setVisible(false);
        botonSeleccionarDeUnLibro.setDisable(false);
        botonSeleccionarDeUnLibro.setVisible(true);
        btnEliminarAutorDeUnLibro.setVisible(true);
        btnEliminarAutorDeUnLibro.setDisable(false);
    }

    public void listaDeUnLibro(Libro libro){
        List<String> listaAutor = LibroAutorDAO.obtenerAutorPorISBN(libro.getISBN());
        List<Autor> listaAutorEntera = AutorDAO.todosLosAutores();

        List<Autor> autoresDelLibro = new ArrayList<>();

        for (Autor autor : listaAutorEntera) {
            if (listaAutor.contains(autor.getNombre())) {
                autoresDelLibro.add(autor);
            }
        }
        this.list.clear();
        this.list = FXCollections.observableArrayList(autoresDelLibro);
        listaAutores.setItems(list);
        botonSeleccionar.setDisable(true);
        botonSeleccionar.setVisible(false);
        btnEliminarAutor.setVisible(false);
        btnEliminarAutor.setDisable(true);
        botonCrearAutor.setDisable(true);
        botonCrearAutor.setVisible(false);
    }


    public void eliminarAutor(MouseEvent mouseEvent) {
        if (listaAutores.getSelectionModel().getSelectedItem() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un autor para eliminar.");
            alerta.showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que quieres eliminar el autor?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            Autor autor = (Autor) listaAutores.getSelectionModel().getSelectedItem();
            AutorDAO.eliminarAutor(autor.getId());


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Autor eliminado correctamente");
            alert.setHeaderText(null);
            alert.setContentText("El autor '" + autor.getNombre() + "' ha sido eliminado correctamente.");
            alert.showAndWait();
        }

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
        btnEliminarAutor.setDisable(true);
        btnEliminarAutor.setVisible(false);
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


    public void SeleccionarAutorDeUnLibro(ActionEvent event) {
        Autor seleccionado = (Autor) listaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar un autor.");
            alerta.showAndWait();
            return;
        }
        List<String> autoresDelLibro = LibroAutorDAO.obtenerAutorPorISBN(libro.getISBN());
        if (autoresDelLibro.contains(seleccionado.getNombre())) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Autor duplicado");
            alerta.setHeaderText(null);
            alerta.setContentText("Este autor ya está asignado a este libro.");
            alerta.showAndWait();
            return;
        }
        LibroAutorDAO.insertarLibroAutor(seleccionado.getId(), libro.getISBN());

        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Éxito");
        exito.setHeaderText(null);
        exito.setContentText("Autor agregado con éxito.");
        exito.showAndWait();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void eliminarAutorDeUnLibro(MouseEvent mouseEvent){
        Autor seleccionado = (Autor) listaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar un autor.");
            alerta.showAndWait();
            return;
        }
        List<String> autoresDelLibro = LibroAutorDAO.obtenerAutorPorISBN(libro.getISBN());
        if (!autoresDelLibro.contains(seleccionado.getNombre())) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Este autor no está asignado a este libro, no se puede eliminar.");
            alerta.showAndWait();
            return;
        }
        LibroAutorDAO.eliminarLibroAutor(seleccionado.getId(), libro.getISBN());

        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Éxito");
        exito.setHeaderText(null);
        exito.setContentText("Autor eliminado con éxito.");
        exito.showAndWait();

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();

    }

}
