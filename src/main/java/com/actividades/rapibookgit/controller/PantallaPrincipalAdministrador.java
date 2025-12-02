package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.LibroAutorDAO;
import com.actividades.rapibookgit.DAO.LibroDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Autor;
import com.actividades.rapibookgit.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class PantallaPrincipalAdministrador {

    @FXML
    private ListView listaLibros;
    @FXML
    private ImageView imagenReinicio;
    @FXML
    private Label noEncontroLibro;
    private ObservableList<Libro> libros;
    private Autor autorSeleccionado;
    private PantallaControllerAutores pantallaControllerAutores;

    /*
    * Metodo que al inicializar el programa carga la lista de todos los libros del sistema sin filtros en un listView
    *
    * */
    public void initialize() {
        File imagenURL = new File("images/reset.png");
        Image image = new Image(imagenURL.toURI().toString());
        imagenReinicio.setImage(image);

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

    /*
     * Metodo de filtro que muestra solamente los libros que sean gratuitos si no existen libros gratuitos el metodo
     * muestra que no hay libros gratis
     *
     * */
    public void gratuitos(ActionEvent event) {
        List<Libro> lista = LibroDAO.todosLosLibros();
        lista.removeIf(libro -> libro.getPrecio() > 0);
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
        List<Libro> lista = LibroDAO.todosLosLibros();
        lista.removeIf(libro -> libro.getPrecio() <= 0);
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
     * Metodo que abre una ventana nueva para añadir un libro a la base de datos el metodo bloquea esta ventana para
     * mejorar el control
     *
     * */
    public void annadirLibro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAñadirLibro.fxml"));
            Stage stage = new Stage();
            Scene scene = null;
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /*
     * Metodo que crea una nueva ventana para actualizar un libro tiene control por si el usuario no selecciona un libro
     * este metodo bloquea la pantalla actual para mejorar el control
     *
     * */
    public void actualizarLibro(ActionEvent event) {
        Libro libroSeleccionado = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un libro para Actualizar.");
            alerta.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAñadirLibro.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load(), 720, 471);
            stage.setScene(scene);
            stage.setResizable(false);
            ControllerAnnadirLibro controller = loader.getController();
            controller.recibirLibro(libroSeleccionado);
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listaLibros.refresh();

    }


    /*
     * Metodo que pregunta si se desea eliminar un libro tiene confirmacion y verificacion de si el usuario no a seleccionado un libro
     * en caso que se desee eliminar el libro el programa lo elimina de la base de datos
     * */
    public void eliminarLibro(ActionEvent event) {
        Libro libroSeleccionado = (Libro) listaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un libro para eliminar.");
            alerta.showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que quieres eliminar el libro: " + libroSeleccionado.getTitulo() + "?");

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            try {
                LibroDAO.eliminarLibro(libroSeleccionado.getISBN());
            }catch (Exception e){
                if (e.getMessage().contains("Referential integrity constraint violation")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No se puede eliminar el libro");
                    alert.setHeaderText("Este libro actualmente esta en un prestamo.");
                    alert.showAndWait();
                    return;
                }
            }

            listaLibros.getItems().remove(libroSeleccionado);

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Eliminación exitosa");
            exito.setHeaderText(null);
            exito.setContentText("El libro fue eliminado correctamente.");
            exito.showAndWait();
        }
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
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.show();
            stage.centerOnScreen();
            Stage ventanaActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Metodo que reinicia la lista de todos los libros esto funciona si quieres quitar los filtros
     *
     * */
    public void reiniciarLista(MouseEvent mouseEvent) {
        List<Libro> lista = LibroDAO.todosLosLibros();

        libros = FXCollections.observableArrayList(lista);
        noEncontroLibro.setText("");
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


    /*
     * Metodo que abre una ventana nueva para poder añadir nuevos autores
     * Este metodo bloquea la pantalla actual para controlar el programa
     *
     * */
    public void autores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAutores.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al abrir la pantalla de autores", e);
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
     * Metodo para controlar un autor es decir para poder eliminarlo añadirlo de un libro
     *
     * */
    public void controlarAutor(MouseEvent mouseEvent) {
        if (listaLibros.getSelectionModel().getSelectedItem() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un Libro.");
            alerta.showAndWait();
            return;
        }
        Libro libro = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAutores.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            PantallaControllerAutores controller = loader.getController();
            controller.listaAutoresParaAnnadirOEliminar(libro);
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
            throw new RuntimeException(e);
        }

    }
    public void recibirAutorSeleccionado(Autor autor) {
        this.autorSeleccionado = autor;
    }

    /*
     * Metodo que al seleccionar un libro se puede ver los autores que tiene ese libro
     * Tiene confirmacion y alerta por si el usuario no selecciona un libro
     * */
    public void verAutores(MouseEvent mouseEvent) {
        if (listaLibros.getSelectionModel().getSelectedItem() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un Libro.");
            alerta.showAndWait();
            return;
        }
        Libro libro = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAutores.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            PantallaControllerAutores controller = loader.getController();
            controller.listaDeUnLibro(libro);
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
            throw new RuntimeException(e);
        }


    }
}
