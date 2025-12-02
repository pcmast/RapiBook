package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.UsuarioDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.model.Usuario;
import com.actividades.rapibookgit.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PantallaLoginController {
    @FXML
    private RadioButton h2;
    @FXML
    private RadioButton mysql;
    @FXML
    private ImageView logo;
    @FXML
    private TextField correo;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Label error;

    /*
    * Metodo que al inicializar la pantalla carga una imagen y carga un ToogleGroup con las bases de datos
    * que se usan en este proyecto
    * */
    public void initialize(){
        File imagenURL = new File("images/libro1.png");
        Image image = new Image(imagenURL.toURI().toString());
        logo.setImage(image);

        ToggleGroup grupoDB = new ToggleGroup();
        h2.setToggleGroup(grupoDB);
        mysql.setToggleGroup(grupoDB);
    }



    /**
     * Metodo que carga otra ventana para introducir los datos del usuario si no tienes una cuenta
     * @param mouseEvent si el usuario a hecho click en registrarse
     */
    public void registrarse(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaRegistrarse.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();
            Scene scene = null;
            scene = new Scene(loader.load());
            Stage stage = new Stage();
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setTitle("RapiBook");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*
    * Metodo que comprueba de la base de datos el usuario que se a introducido si existe inicia dependiendo si es
    * administrador o usuario normal
    *
    * */
    public void inicioSesion(ActionEvent event) {
        String email = correo.getText();
        String password = contrasena.getText();

        List<Usuario> usuarios = UsuarioDAO.todosUsuarios();
        for (Usuario usuario : usuarios){
            if (usuario.getEmail().equals(email) && Utilidades.verificarPassword(password, usuario.getContrasena())){
            UsuarioActualController.getInstance().setUsuario(usuario);
            if (usuario.isAdministrador()){
                iniciarSesionAdministrador(event);
            }else {
                iniciarSesionUsuario(event);
            }
            }else {
                error.setText("Tu correo o contraseña no coinciden");
            }

        }


    }
    // Metodo que inicia sesion como administrador crea una ventana de administrador
    public void iniciarSesionAdministrador(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPrincipalAdministrador.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(loader.load());
            stage.setTitle("RapiBook");
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //Metodo que inicia sesion como usuario normal sin privilegios
    public void iniciarSesionUsuario(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPrincipalUsuario.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = null;
            stage.setTitle("RapiBook");
            scene = new Scene(loader.load(), 1100, 600);
            stage.setScene(scene);
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo que asigna la base de datos a H2
    public void usarH2(ActionEvent event) {
        ConnectionSelector.useH2();

    }
    //Metodo que asigna la base de datos a usar con MySql con phpMyadmin
    public void usarMySQL(ActionEvent event) {
        ConnectionSelector.useMySQL();

    }
}