package com.actividades.rapibookgit.baseDatos;

import java.sql.Connection;

public class ConnectionSelector {

    private static boolean useH2 = false;

    public static void useMySQL() {
        useH2 = false;
    }
    
    public static void useH2() {
        useH2 = true;
    }

    public static Connection getConnection() {
        if (useH2) {
            return H2ConnectionDB.getConnection();
        } else {
            return ConnectionDB.getConnection();
        }
    }
}
