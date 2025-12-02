package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.LibroAutorDAO;
import com.actividades.rapibookgit.DAO.LibroDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Autor;
import com.actividades.rapibookgit.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;

import com.actividades.rapibookgit.utilidades.Utilidades;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerAnnadirLibro {


    @FXML
    private TextField ISBN;
    @FXML
    private ImageView imagen;
    @FXML
    private TextField titulo;
    @FXML
    private TextField anno;
    @FXML
    private TextField editorialNombre;
    @FXML
    private TextField precio;
    @FXML
    private Label camposVacios;
    @FXML
    private Label nombreAutor;
    @FXML
    private Button actualizarLibrobtn;
    @FXML
    private Button annadirLibrobtn;
    private Autor autorSeleccionado;
    private String ruta;
    private Libro libroActual;

    /*
    * Metodo que al inicilizar la pantalla coge una imagen y la asigna a la imagen de la pantalla y desabilita
    * Botones que no son necesarios al principio del programa
    *
    * */
    public void initialize() {
        File file = new File("images/libro2.png");
        Image image = new Image(file.toURI().toString());
        imagen.setImage(image);
        actualizarLibrobtn.setVisible(false);
        actualizarLibrobtn.setDisable(true);
        annadirLibrobtn.setDisable(false);
        annadirLibrobtn.setVisible(true);
    }
    /*
     *Metodo que es llamado de otra pantalla y recibe un libro y lo asigna a un libro de esta clase
     * */
    public void recibirLibro(Libro libro) {
        actualizarLibrobtn.setVisible(true);
        actualizarLibrobtn.setDisable(false);
        annadirLibrobtn.setDisable(true);
        annadirLibrobtn.setVisible(false);

        this.libroActual = libro;

        ISBN.setText(libro.getISBN());
        ISBN.setDisable(true);
        titulo.setText(libro.getTitulo());
        anno.setText(libro.getAno());
        editorialNombre.setText(libro.getEditorial());
        precio.setText(String.valueOf(libro.getPrecio()));

        File file = new File(libro.getPortada());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imagen.setImage(image);
            ruta = libro.getPortada();
        }
    }

    /*
     *Metodo que actualiza la informacion basica de un libro como podria ser el titulo año etc
     * luego de actualizar el metodo cierra esta ventana
     * */
    public void actualizarLibro(ActionEvent event) {
        try {
            int precioLibro = Integer.parseInt(precio.getText());
            if (precioLibro < 0) {
                camposVacios.setText("El precio no puede ser negativo");
                return;
            }
            libroActual.setTitulo(titulo.getText());
            libroActual.setAno(anno.getText());
            libroActual.setEditorial(editorialNombre.getText());
            libroActual.setPrecio(Integer.parseInt(precio.getText()));
            libroActual.setPortada(ruta);
            LibroDAO.actualizarLibro(libroActual);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Actualización exitosa");
            exito.setHeaderText(null);
            exito.setContentText("El libro fue actualizado correctamente.");
            exito.showAndWait();
        } catch (NumberFormatException e) {
            camposVacios.setText("Introduce un precio válido (solo números)");
        }
    }

    /*
     * Metodo que añade un libro con todos sus datos y un autor y luego se encarga de registrarlo en la base de datos
     * Tiene comprobaciones de todo tipo por si no se escriben datos.
     * Al añadir un libro cierra esta ventana
     * */
    public void annadirLibro(MouseEvent mouseEvent) {
        boolean existe = true;
        if (titulo.getText().isEmpty() || anno.getText().isEmpty() || editorialNombre.getText().isEmpty() || precio.getText().isEmpty()) {
            camposVacios.setText("Debes introducir todos los datos");
        } else if (anno.getText().length() > 4) {
            camposVacios.setText("Introduce una fecha valida");
        } else {
            int precioLibro;
            try {
                precioLibro = Integer.parseInt(precio.getText());
                if (precioLibro < 0) {
                    camposVacios.setText("El precio no puede ser negativo");
                    return;
                }
            } catch (NumberFormatException e) {
                camposVacios.setText("Introduce un precio válido (solo números)");
                return;
            }


            String isbn = ISBN.getText();
            if (autorSeleccionado != null) {
                if (Utilidades.validarISBN(isbn)) {
                    List<Libro> libros = LibroDAO.todosLosLibros();
                    for (Libro libro : libros) {
                        if (libro.getISBN().equals(isbn)) {
                            camposVacios.setText("Este libro ya existe en el sistema");
                            existe = false;
                        }

                    }

                    if (existe) {
                        if (this.ruta == null) {
                            File file = new File("images/libro2.png");
                            ruta = file.getAbsolutePath();
                        }
                        int precio = Integer.parseInt(this.precio.getText());
                        if (precio >= 0) {
                            LibroDAO.insertarLibro(isbn, titulo.getText(), anno.getText(), editorialNombre.getText(), true, this.ruta, precio);
                        } else {
                            LibroDAO.insertarLibro(isbn, titulo.getText(), anno.getText(), editorialNombre.getText(), false, this.ruta, precio);
                        }
                        if (autorSeleccionado != null) {
                            int idAutor = autorSeleccionado.getId();
                            LibroAutorDAO.insertarLibroAutor(idAutor, isbn);
                        }
                        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        stage.close();
                    }
                } else {
                    camposVacios.setText("Debes introducir un ISBN valido");
                }
            } else {
                camposVacios.setText("Debes seleccionar un autor");
            }
        }
    }

    /*
     * Metodo que usa la clase FileChooser para seleccionar una imagen que quiera el usuario para un libro.
     * Luego pega esa imagen en la carpeta del proyecto images
     *
     * */
    public void seleccionarImagen(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File imagenSeleccionada = fileChooser.showOpenDialog(
                ((Node) mouseEvent.getSource()).getScene().getWindow()
        );

        if (imagenSeleccionada != null) {
            File carpetaDestino = new File("images");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }
            File archivoDestino = new File(carpetaDestino, imagenSeleccionada.getName());

            try (InputStream in = new FileInputStream(imagenSeleccionada);
                 OutputStream out = new FileOutputStream(archivoDestino)) {

                byte[] buffer = new byte[4096];
                int bytesLeidos;

                while ((bytesLeidos = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }

                out.flush();
                ruta = "images/" + imagenSeleccionada.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ruta != null) {
            File file = new File(ruta);
            Image image = new Image(file.toURI().toString());
            imagen.setImage(image);
        } else {
            camposVacios.setText("No se seleccionó ninguna imagen");
        }
    }

    /*
     * Metodo llamado desde otro controlador para asignar desde el otro un autor y hacer las operaciones correspondientes con el
     *
     * */
    public void recibirAutorSeleccionado(Autor autor) {
        this.autorSeleccionado = autor;
        nombreAutor.setText(autor.getNombre());
    }

    /*
     * Metodo asignado a un boton que abre una ventana nueva para añadir un autor
     * Esta ventana se encarga de bloquear la pantalla actual para poder tener un control del programa mejor
     * */
    public void annadirAutor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaAutores.fxml"));
            Stage stage = new Stage();
            Scene scene = null;
            scene = new Scene(loader.load());
            PantallaControllerAutores controllerAutores = loader.getController();
            controllerAutores.setControllerAnnadirLibro(this);
            controllerAutores.inicializarBotones();
            stage.setTitle("RapiBook");
            stage.setScene(scene);
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setResizable(false);
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
