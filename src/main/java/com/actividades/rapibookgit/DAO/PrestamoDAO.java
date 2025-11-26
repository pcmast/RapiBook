package com.actividades.rapibookgit.DAO;

import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.model.Libro;
import com.actividades.rapibookgit.model.Prestamo;
import com.actividades.rapibookgit.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PrestamoDAO {
    private static final String SQL_Todos = "SELECT * FROM Prestamo";
    private static final String SQL_ANNADIR = "";

    public static List<Prestamo> todosPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_Todos);
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email_usuario");
                String ISBNLibro = rs.getString("ISBN_libro");
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Date fechaDevolucion= rs.getDate("fecha_devolucion");
                Usuario usuario = new Usuario(email);
                Libro libro = new Libro(ISBNLibro);
                Prestamo prestamo = new Prestamo(id,usuario,libro,fechaPrestamo.toString(),fechaDevolucion.toString());
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prestamos;
    }


    public static void insertarPrestamo(String email, String contrasena, String nombre, boolean administrador) {
        Connection con = ConnectionDB.getConnection();
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