package com.actividades.rapibookgit.controller;

import com.actividades.rapibookgit.DAO.UsuarioDAO;
import com.actividades.rapibookgit.HelloApplication;
import com.actividades.rapibookgit.baseDatos.XMLManager;
import com.actividades.rapibookgit.model.ClaveWrapper;
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

public class PantallaRegisterController {


    @FXML
    private TextField nombre;
    @FXML
    private TextField correo;
    @FXML
    private PasswordField contrasena;
    @FXML
    private PasswordField repetirContra;
    @FXML
    private ImageView logo;
    @FXML
    private TextField administrador;
    @FXML
    private Label vacios;
    @FXML
    private Label claveAdmin;

    public void initialize() {
        File imagenURL = new File("images/libro1.png");
        Image image = new Image(imagenURL.toURI().toString());
        logo.setImage(image);
    }

    /*
    * Metodo que recoge los datos introducidos por el usuario y comprueba si estan correctos o si a dejado
    * alguno vacio en el caso que este correcto registra ese mismo usuario en la base de datos
    * Comprueba mediante validacion en correo y hashea la contraseña
    * En caso que el usuario se haya registrado abre la ventana de inicio sesion
    * */
    public void registrarse(ActionEvent actionEvent) {
        String nombre = this.nombre.getText();
        String correo = this.correo.getText();
        String contra = contrasena.getText();
        String repContra = repetirContra.getText();
        String admin = administrador.getText();
        ClaveWrapper wrapper = XMLManager.readXML(new ClaveWrapper(), "clave.xml");
        String claveXML = wrapper.getValue();
        boolean existe = false;
        boolean avanzar = false;
        boolean iniciadoSesion = false;
        if (Utilidades.validarCorreo(correo)) {
            if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || repContra.isEmpty()) {
                vacios.setText("Debes introducir todos los datos");
            } else {
                if (!contra.equals(repContra)) {
                    vacios.setText("Las contraseñas no coinciden");
                } else {
                    avanzar = true;
                }
            }
            if (avanzar) {
                List<Usuario> list = UsuarioDAO.todosUsuarios();
                boolean claveCorrecta = false;
                for (Usuario usuario : list) {
                    if (usuario.getEmail().equals(correo)) {
                        existe = true;
                    }
                }
                String contraHasheada = Utilidades.hashPassword(contra);
                if (!admin.isEmpty()) {
                    if (admin.equals(claveXML)) {
                        claveCorrecta = true;
                    } else {
                        claveAdmin.setText("La clave no coincide");
                    }
                }
                if (existe) {
                    vacios.setText("Usuario ya existen en el sistema");
                } else {
                    vacios.setText("");
                    if (claveCorrecta) {
                        UsuarioDAO.insertarUsuarios(correo, contraHasheada, nombre, true);
                        iniciadoSesion = true;
                    } else if (!admin.isEmpty()) {
                        claveAdmin.setText("La clave no coincide");
                    } else {
                        UsuarioDAO.insertarUsuarios(correo, contraHasheada, nombre, false);
                        iniciadoSesion = true;
                    }

                }
            }
        } else {
            vacios.setText("Introduce un correo valido");
        }

        if (iniciadoSesion) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
            Stage stage = new Stage();
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
                stage.setTitle("RapiBook");
                stage.setScene(scene);
                File imagenURL = new File("images/biblioteca.png");
                Image image = new Image(imagenURL.toURI().toString());
                stage.getIcons().add(image);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //Metodo que vuelve a la ventana de inicio de sesión
    public void iniciaSesion(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pantallaLogin.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {

            scene = new Scene(fxmlLoader.load(), 720, 471);
            stage.setTitle("Rapibook");
            stage.setScene(scene);
            File imagenURL = new File("images/biblioteca.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
