package com.actividades.rapibookgit.DAO;

import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.model.Autor;
import com.actividades.rapibookgit.model.Libro;
import com.actividades.rapibookgit.model.LibroAutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroAutorDAO {
    private static final String SQL_BUSCAR_AUTOR = "SELECT id FROM autor WHERE nombre = ?";
    private static final String SQL_LIBROS_POR_AUTOR = "SELECT ISBN_autor FROM libro_autor WHERE id_autor = ?";
    private static final String SQL_INSERTAR = "INSERT INTO libroautor (id_autor, ISBN_libro) VALUES (?, ?)";
    private static final String SQL_AUTORES_POR_ISBN = "SELECT a.nombre FROM autor a " + "JOIN libroautor la ON a.id = la.id_autor " + "WHERE la.ISBN_libro = ?";
    private static final String SQL_ELIMINAR_LIBRO_AUTOR = "DELETE FROM libroautor WHERE ISBN_libro = ? and id_autor = ?";


    public static void eliminarLibroAutor(int id, String isbn) {
        try {
            Connection conn = ConnectionSelector.getConnection();
            PreparedStatement stmtLibroAutor = null;


            try {
                stmtLibroAutor = conn.prepareStatement(SQL_ELIMINAR_LIBRO_AUTOR);
                stmtLibroAutor.setString(1, isbn);
                stmtLibroAutor.setInt(2, id);
                stmtLibroAutor.executeUpdate();


            } finally {
                if (stmtLibroAutor != null) stmtLibroAutor.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> obtenerAutorPorISBN(String isbn) {
        List<String> autores = new ArrayList<>();

        try {
            Connection conn = ConnectionSelector.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.prepareStatement(SQL_AUTORES_POR_ISBN);
                stmt.setString(1, isbn);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    autores.add(rs.getString("nombre"));
                }

            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autores;
    }

    public static void insertarLibroAutor(int idAutor, String isbnLibro) {
        try (Connection con = ConnectionSelector.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERTAR)) {

            stmt.setInt(1, idAutor);
            stmt.setString(2, isbnLibro);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Libro> librosPorNombreAutor(String nombreAutor) {
        List<Libro> lista = new ArrayList<>();

        try (Connection con = ConnectionSelector.getConnection();) {

            PreparedStatement stmtAutor = con.prepareStatement(SQL_BUSCAR_AUTOR);
            stmtAutor.setString(1, nombreAutor);
            ResultSet rsAutor = stmtAutor.executeQuery();

            if (rsAutor.next()) {
                int idAutor = rsAutor.getInt("id");


                PreparedStatement stmtLibros = con.prepareStatement(SQL_LIBROS_POR_AUTOR);
                stmtLibros.setInt(1, idAutor);
                ResultSet rsLibros = stmtLibros.executeQuery();

                List<Libro> todosLosLibros = LibroDAO.todosLosLibros();

                while (rsLibros.next()) {
                    String isbn = rsLibros.getString("ISBN_autor");


                    for (Libro libro : todosLosLibros) {
                        if (libro.getISBN().equals(isbn)) {
                            lista.add(libro);
                            break;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }


}


