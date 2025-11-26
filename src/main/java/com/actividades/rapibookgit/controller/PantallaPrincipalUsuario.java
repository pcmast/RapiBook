package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.LibroDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.actividades.rapibookgit.DAO.LibroAutorDAO;

import java.io.IOException;
import java.util.List;

public class PantallaPrincipalUsuario {


    public ListView<Libro> listaLibros;

    public TextField buscador;
    public Label nombreUsuario;

    private ObservableList<Libro> libros = cargarLista();


    public void initialize() {
        nombreUsuario.setText(UsuarioActualController.getInstance().getUsuario().getNombre());
        List<Libro> lista = LibroDAO.todosLosLibros();

        libros = FXCollections.observableArrayList(lista);

        listaLibros.setItems(libros);

        listaLibros.setCellFactory(param -> new ListCell<Libro>() {
            private VBox vbox = new VBox(5);
            private ImageView imageView = new ImageView();
            private Label tituloLabel = new Label();
            private Label autoresLabel = new Label();
            private Label precioLabel = new Label(); // <- nuevo

            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    Image img = new Image("file:" + item.getPortada(), 100, 150, true, true);
                    imageView.setImage(img);


                    tituloLabel.setText(item.getTitulo());


                    List<String> autores = LibroAutorDAO.obtenerAutorPorISBN(item.getISBN());
                    String textoAutores = autores.isEmpty() ? "Autor desconocido" : String.join(", ", autores);
                    autoresLabel.setText("Autor: " + textoAutores);


                    precioLabel.setText("Precio: $" + item.getPrecio());


                    vbox.getChildren().setAll(imageView, tituloLabel, autoresLabel, precioLabel);

                    setGraphic(vbox);
                }
            }
        });

    }

    public ObservableList<Libro> cargarLista() {
        List<Libro> libros = LibroDAO.todosLosLibros();
        return FXCollections.observableArrayList(libros);
    }

    public void gratuitos(ActionEvent event) {
        List<Libro> lista = LibroDAO.todosLosLibros(); // Trae todos
        lista.removeIf(libro -> libro.getPrecio() > 0); // Solo los gratis
        libros = FXCollections.observableArrayList(lista);

        listaLibros.setItems(libros);

        listaLibros.setCellFactory(param -> new ListCell<Libro>() {
            private VBox vbox = new VBox(5);
            private ImageView imageView = new ImageView();
            private Label tituloLabel = new Label();
            private Label autoresLabel = new Label();
            private Label precioLabel = new Label();

            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    Image img = new Image("file:" + item.getPortada(), 100, 150, true, true);
                    imageView.setImage(img);

                    tituloLabel.setText(item.getTitulo());

                    List<String> autores = LibroAutorDAO.obtenerAutorPorISBN(item.getISBN());
                    String textoAutores = autores.isEmpty() ? "Autor desconocido" : String.join(", ", autores);
                    autoresLabel.setText("Autor: " + textoAutores);

                    precioLabel.setText("Precio: $" + item.getPrecio());

                    vbox.getChildren().setAll(imageView, tituloLabel, autoresLabel, precioLabel);

                    setGraphic(vbox);
                }
            }
        });
    }

    public void dePago(ActionEvent event) {
        List<Libro> lista = LibroDAO.todosLosLibros(); // Trae todos
        lista.removeIf(libro -> libro.getPrecio() <= 0); // Solo los de pago
        libros = FXCollections.observableArrayList(lista);

        listaLibros.setItems(libros);

        listaLibros.setCellFactory(param -> new ListCell<Libro>() {
            private VBox vbox = new VBox(5);
            private ImageView imageView = new ImageView();
            private Label tituloLabel = new Label();
            private Label autoresLabel = new Label();
            private Label precioLabel = new Label();

            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    Image img = new Image("file:" + item.getPortada(), 100, 150, true, true);
                    imageView.setImage(img);

                    tituloLabel.setText(item.getTitulo());

                    List<String> autores = LibroAutorDAO.obtenerAutorPorISBN(item.getISBN());
                    String textoAutores = autores.isEmpty() ? "Autor desconocido" : String.join(", ", autores);
                    autoresLabel.setText("Autor: " + textoAutores);

                    precioLabel.setText("Precio: $" + item.getPrecio());

                    vbox.getChildren().setAll(imageView, tituloLabel, autoresLabel, precioLabel);

                    setGraphic(vbox);
                }
            }
        });
    }


    public void cerrarSesion(MouseEvent mouseEvent) {
        UsuarioActualController.getInstance().setUsuario(null);
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 640, 400);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
            Stage ventanaActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void buscarLibro(KeyEvent keyEvent) {
        TextField textField = (TextField) keyEvent.getSource();
        String filtro = textField.getText().toLowerCase();

        FilteredList<Libro> librosFiltrados = new FilteredList<>(libros, libro -> true);

        librosFiltrados.setPredicate(libro -> {
            if (filtro == null || filtro.isEmpty()) {
                return true;
            }
            return libro.getTitulo().toLowerCase().contains(filtro);
        });

        listaLibros.setItems(FXCollections.observableArrayList(librosFiltrados));

    }


    public void pedirPrestamo(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPedirPrestamo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void verPrestamos(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaVerPrestamo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}