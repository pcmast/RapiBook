package com.actividades.rapibookgit.DAO;

import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final static String SQL_ALL = "SELECT * FROM usuario;";
    private final static String SQL_ANNADIR = "INSERT INTO usuario (email, contrasena, nombre, administrador) VALUES (?, ?, ?, ?)";

    public static List<Usuario> todosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                String nombre = rs.getString("nombre");
                boolean administrador = rs.getBoolean("administrador");

                Usuario usuario = new Usuario(email, contrasena, nombre, administrador);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public static void insertarUsuarios(String email, String contrasena, String nombre, boolean administrador) {
        Connection con = ConnectionSelector.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SQL_ANNADIR);

            stmt.setString(1, email);
            stmt.setString(2, contrasena);
            stmt.setString(3, nombre);
            stmt.setBoolean(4, administrador);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
