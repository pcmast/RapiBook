package com.actividades.rapibookgit.DAO;
import com.actividades.rapibookgit.baseDatos.ConnectionDB;
import com.actividades.rapibookgit.baseDatos.ConnectionSelector;
import com.actividades.rapibookgit.model.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AutorDAO {


    private static final String SQL_INSERTAR = "INSERT INTO autor(nombre, biografia) VALUES(?, ?)";
    private static final String SQL_TODOS = "SELECT * FROM autor";
    private static final String SQL_BUSCAR_ID = "SELECT * FROM autor WHERE id = ?";





    public static void insertarAutor(String nombre, String biografia) {
        Connection con = ConnectionSelector.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(SQL_INSERTAR);
            stmt.setString(1, nombre);
            stmt.setString(2, biografia);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Autor> todosLosAutores() {
        List<Autor> autores = new ArrayList<>();
        Connection con = ConnectionSelector.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(SQL_TODOS);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String biografia = rs.getString("biografia");
                autores.add(new Autor(id, nombre, biografia));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return autores;
    }


}
