package com.actividades.rapibookgit.DAO;

import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private static final String SQL_Todos = "SELECT * FROM libro;";
    private static final String SQL_POR_EDITORIAL = "SELECT * FROM libro WHERE editorial = ?;";
    private static final String SQL_Gratis = "SELECT * FROM libro WHERE esGratis = 1;";
    private static final String SQL_pago = "SELECT * FROM libro WHERE esGratis = 0;";
    private static final String SQL_insertar = "INSERT INTO libro (ISBN, titulo, ano, editorial, esGratis, portada, precio) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_ELIMINAR_LIBRO_AUTOR = "DELETE FROM libroautor WHERE ISBN_libro = ?";
    private static final String SQL_ELIMINAR_LIBRO = "DELETE FROM libro WHERE ISBN = ?";
    private static final String SQL_OBTENER_POR_ISBN = "SELECT * FROM libro WHERE ISBN = ?;";


    public static void eliminarLibro(String isbn) {
        try {
            Connection conn = ConnectionSelector.getConnection();

            PreparedStatement stmtLibroAutor = null;
            PreparedStatement stmtLibro = null;

            try {
                stmtLibroAutor = conn.prepareStatement(SQL_ELIMINAR_LIBRO_AUTOR);
                stmtLibroAutor.setString(1, isbn);
                stmtLibroAutor.executeUpdate();

                stmtLibro = conn.prepareStatement(SQL_ELIMINAR_LIBRO);
                stmtLibro.setString(1, isbn);
                stmtLibro.executeUpdate();

            } finally {
                if (stmtLibroAutor != null) stmtLibroAutor.close();
                if (stmtLibro != null) stmtLibro.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Libro> todosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();

        try (PreparedStatement stmt = con.prepareStatement(SQL_Todos);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String titulo = rs.getString("titulo");
                String ano = rs.getString("ano");
                String editorial = rs.getString("editorial");
                boolean esGratis = rs.getBoolean("esGratis");
                String portada = rs.getString("portada");
                int precio = rs.getInt("precio");
                Libro libro = new Libro(ISBN, titulo, ano, editorial, esGratis, portada, precio);
                libros.add(libro);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }


    public static List<Libro> librosPorEditorial(String editorial) {
        List<Libro> libros = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement(SQL_POR_EDITORIAL);
            stmt.setString(1, editorial);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String titulo = rs.getString("titulo");
                String ano = rs.getString("ano");
                boolean esGratis = rs.getBoolean("esGratis");
                String portada = rs.getString("portada");
                int precio = rs.getInt("precio");

                Libro libro = new Libro(ISBN, titulo, ano, editorial, esGratis, portada, precio);
                libros.add(libro);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }

    public static List<Libro> librosGratis() {
        List<Libro> libros = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement(SQL_Gratis);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String titulo = rs.getString("titulo");
                String ano = rs.getString("ano");
                boolean esGratis = rs.getBoolean("esGratis");
                String Editorial = rs.getString("editorial");
                String portada = rs.getString("portada");
                int precio = rs.getInt("precio");


                Libro libro = new Libro(ISBN, titulo, ano, Editorial, esGratis, portada, precio);
                libros.add(libro);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return libros;
    }


    public static void insertarLibro(String ISBN, String titulo, String ano, String editorial, boolean esGratis, String portada, int precio) {

        Connection con = ConnectionSelector.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement(SQL_insertar);

            stmt.setString(1, ISBN);
            stmt.setString(2, titulo);
            stmt.setString(3, ano);
            stmt.setString(4, editorial);
            stmt.setBoolean(5, esGratis);
            stmt.setString(6, portada);
            stmt.setInt(7, precio);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Libro obtenerLibroPorISBN(String isbn) {
        try (Connection con = ConnectionSelector.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_OBTENER_POR_ISBN)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String titulo = rs.getString("titulo");
                String ano = rs.getString("ano");
                String editorial = rs.getString("editorial");
                boolean esGratis = rs.getBoolean("esGratis");
                String portada = rs.getString("portada");
                int precio = rs.getInt("precio");

                return new Libro(ISBN, titulo, ano, editorial, esGratis, portada, precio);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
