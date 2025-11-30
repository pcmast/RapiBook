package com.actividades.rapibookgit.DAO;

import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.controller.UsuarioActualController;
import com.actividades.rapibookgit.model.Libro;
import com.actividades.rapibookgit.model.Prestamo;
import com.actividades.rapibookgit.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PrestamoDAO {
    private static final String SQL_INSERTAR = "INSERT INTO prestamo (email_usuario, ISBN_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
    private static final String SQL_OBTENER_POR_EMAIL = "SELECT * FROM prestamo WHERE email_usuario = ?";

    public static void insertarPrestamo(Prestamo prestamo) {
        try (Connection con = ConnectionSelector.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERTAR)) {


            Usuario usuario = prestamo.getUsuario();
            stmt.setString(1, usuario.getEmail());

            Libro libro = prestamo.getLibro();
            stmt.setString(2, libro.getISBN());
            stmt.setString(3, prestamo.getFechaPrestamo());
            stmt.setString(4, prestamo.getFechaDevolucion());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prestamo> todosPrestamosPorEmail(String emailUsuario) {
        List<Prestamo> prestamos = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_OBTENER_POR_EMAIL);
            pstmt.setString(1, emailUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("ISBN_libro");
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Date fechaDevolucion = rs.getDate("fecha_devolucion");
                Libro libro = new Libro(isbn);
                Prestamo prestamo = new Prestamo(id, UsuarioActualController.getInstance().getUsuario(),libro, fechaPrestamo.toString(), fechaDevolucion.toString());
                prestamos.add(prestamo);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prestamos;
    }


}