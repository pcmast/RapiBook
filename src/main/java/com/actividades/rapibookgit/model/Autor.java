package com.actividades.rapibookgit.model;

public class Autor {
    private int id;
    private String nombre;
    private String biografia;

    public Autor(int id, String nombre, String biografia) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
    }


    public Autor(String nombre, String biografia) {
        this.nombre = nombre;
        this.biografia = biografia;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Biografía: " + biografia;
    }
}
