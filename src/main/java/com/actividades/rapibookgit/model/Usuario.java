package com.actividades.rapibookgit.model;

public class Usuario {

    private String email;
    private String contrasena;
    private String nombre;
    private boolean administrador;

    public Usuario(String email, String contrasena, String nombre, boolean administrador) {
        this.email = email;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.administrador = administrador;
    }

    public Usuario(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}
