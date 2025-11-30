package com.actividades.rapibookgit.baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionDB {

    private static final String FILE = "h2connection.xml";
    private static Connection con;
    private static H2ConnectionDB _instance;

    private H2ConnectionDB() {
        H2ConnectionProperties properties =
                XMLManager.readXML(new H2ConnectionProperties(), FILE);

        try {
            // Cargar driver
            Class.forName("org.h2.Driver");

            // Crear conexión
            con = DriverManager.getConnection(
                    properties.getUrl(),
                    properties.getUser(),
                    properties.getPassword()
            );

        } catch (Exception e) {
            e.printStackTrace();
            con = null;
        }
    }

    public static Connection getConnection() {
        try {
            if (_instance == null || con == null || con.isClosed()) {
                _instance = new H2ConnectionDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
