package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.LibroAutorDAO;
import com.actividades.rapibookgit.DAO.LibroDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Autor;
import com.actividades.rapibookgit.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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


    public TextField ISBN;
    public ImageView imagen;
    public TextField titulo;
    public TextField anno;
    public TextField editorialNombre;
    public TextField precio;
    public Label camposVacios;
    public Label nombreAutor;
    private Autor autorSeleccionado;
    private String ruta;

    public void initialize(){
        File file = new File("images/libro2.png");
        Image image = new Image(file.toURI().toString());
        imagen.setImage(image);
    }



    public void annadirLibro(MouseEvent mouseEvent) {
        boolean existe = true;
        if (titulo.getText().isEmpty() || anno.getText().isEmpty() || editorialNombre.getText().isEmpty() || precio.getText().isEmpty()){
            camposVacios.setText("Debes introducir todos los datos");
        }else if (anno.getText().length() > 4){
            camposVacios.setText("Introduce una fecha valida");
        }else {

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
        }else {
            camposVacios.setText("Debes seleccionar un autor");
        }
        }
    }


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
                ruta ="images/" + imagenSeleccionada.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(ruta);
        Image image = new Image(file.toURI().toString());
        imagen.setImage(image);
    }


    public void recibirAutorSeleccionado(Autor autor) {
        this.autorSeleccionado = autor;
        nombreAutor.setText(autor.getNombre());
    }



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
