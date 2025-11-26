package com.actividades.rapibookgit.utilidades;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

public class Utilidades {

    //Metodo que hace un hash de una contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //Metodo que verifica si las contraseñas coinciden
    public static boolean verificarPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static boolean validarISBN(String isbn) {

        isbn = isbn.replaceAll("-", "").trim();


        if (isbn.length() != 10 && isbn.length() != 13) {
            return false;
        }

        return isbn.matches("\\d+");
    }

    public static boolean validarCorreo(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return Pattern.matches(regex, email);
    }


}
