package com.actividades.rapibookgit.baseDatos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String FILE = "connection.xml";
    private static Connection con;
    private static ConnectionDB _instance;

    // Constructor privado
    private ConnectionDB() {
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);

        try {
            con = DriverManager.getConnection(
                    properties.getUrl(),
                    properties.getUser(),
                    properties.getPassword()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    public static Connection getConnection() {
        try {
            if (_instance == null || con == null || con.isClosed()) {
                _instance = new ConnectionDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}
