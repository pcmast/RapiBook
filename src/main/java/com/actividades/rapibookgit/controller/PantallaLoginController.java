package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.UsuarioDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.model.Usuario;
import com.actividades.rapibookgit.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PantallaLoginController {
    @FXML
    private ImageView logo;
    public TextField correo;
    public PasswordField contrasena;
    public Label error;


    public void initialize(){
        File imagenURL = new File("images/libro1.png");
        Image image = new Image(imagenURL.toURI().toString());
        logo.setImage(image);
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
            stage.setTitle("RapiBook");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

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

    public void iniciarSesionAdministrador(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPrincipalAdministrador.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(loader.load());
            stage.setTitle("RapiBook");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void iniciarSesionUsuario(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("pantallaPrincipalUsuario.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = null;
            stage.setTitle("RapiBook");
            scene = new Scene(loader.load(), 1100, 600);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}