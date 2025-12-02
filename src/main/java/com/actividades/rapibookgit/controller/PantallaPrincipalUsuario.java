package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.LibroDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.actividades.rapibookgit.DAO.LibroAutorDAO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PantallaPrincipalUsuario {


    @FXML
    private ListView<Libro> listaLibros;

    @FXML
    private TextField buscador;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label noEncontroLibro;

    private ObservableList<Libro> libros = cargarLista();

    /*
    * Metodo que al inicializar el programa asigna todos los libros existentes en el sistema a un listView para
    * mostrarselo al usuario
    *
    * */
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

    //Metodo que carga la lista de los libros en el ObservableList
    public ObservableList<Libro> cargarLista() {
        List<Libro> libros = LibroDAO.todosLosLibros();
        return FXCollections.observableArrayList(libros);
    }

    /*
     * Metodo de filtro que muestra solamente los libros que sean gratuitos si no existen libros gratuitos el metodo
     * muestra que no hay libros gratis
     *
     * */
    public void gratuitos(ActionEvent event) {
        List<Libro> lista = LibroDAO.todosLosLibros(); // Trae todos
        lista.removeIf(libro -> libro.getPrecio() > 0); // Solo los gratis
        libros = FXCollections.observableArrayList(lista);
        if (libros.isEmpty()) {
            noEncontroLibro.setText("No se encontraron libros gratuitos");
        } else {
            noEncontroLibro.setText("");
        }
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
    /*
     * Metodo de filtro que muestra solamente los libros que sean de pago si no existen libros de pago el metodo
     * muestra que no hay libros de pago "No se encontraron libros de pago"
     * */
    public void dePago(ActionEvent event) {
        List<Libro> lista = LibroDAO.todosLosLibros(); // Trae todos
        lista.removeIf(libro -> libro.getPrecio() <= 0); // Solo los de pago
        libros = FXCollections.observableArrayList(lista);
        if (libros.isEmpty()) {
            noEncontroLibro.setText("No se encontraron libros de pago");
        } else {
            noEncontroLibro.setText("");
        }
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


    /*
     * Metodo que abre la ventana login y cierra sesion del usuario actual es decir setea a null el UsuarioActual
     *
     * */
    public void cerrarSesion(MouseEvent mouseEvent) {
        UsuarioActualController.getInstance().setUsuario(null);
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 720, 471);
            stage.setScene(scene);
            stage.setResizable(false);
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setTitle("RapiBook");
            stage.show();
            stage.centerOnScreen();
            Stage ventanaActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * Metodo que busca un libro por sui titulo si no encuentra ninguno muestra un mensaje
     *
     * */
    public void buscarLibro(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String filtro = textField.getText().toLowerCase();

        FilteredList<Libro> librosFiltrados = new FilteredList<>(libros, libro -> true);

        if (filtro == null || filtro.isEmpty()) {
            listaLibros.setItems(libros);
            noEncontroLibro.setText("");
            return;
        }

        librosFiltrados.setPredicate(libro -> libro.getTitulo().toLowerCase().contains(filtro));

        if (librosFiltrados.isEmpty()) {
            noEncontroLibro.setText("No se encontraron libros con ese criterio");
        } else {
            noEncontroLibro.setText("");
        }

        listaLibros.setItems(FXCollections.observableArrayList(librosFiltrados));
    }


    /*
    * Metodo que abre la ventana de pedir un prestamo al usuario
    * Tiene comprobacion de todo por ejemplo si el usuario no selecciono ningun libro
    *
    * */
    public void pedirPrestamo(MouseEvent mouseEvent) {
        Libro libroSeleccionado = (Libro) listaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un libro primero.");
            alerta.showAndWait();
            return;
        }
        try {

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPedirPrestamo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            PantallaControllerPedirPrestamo controller = loader.getController();
            controller.setLibro(libroSeleccionado);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
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

    //Metodo que abre otra ventana que muestra los prestamos de un usuario
    public void verPrestamos(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaVerPrestamo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
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