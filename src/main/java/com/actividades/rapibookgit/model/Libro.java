package com.actividades.rapibookgit.model;

import java.util.Objects;

public class Libro {
    private String ISBN;
    private String titulo;
    private String ano;
    private String editorial;
    private boolean esGratis;
    private String portada;
    private int precio;

    public Libro(String ISBN, String titulo, String ano, String editorial, boolean esGratis, String portada, int precio) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.ano = ano;
        this.editorial = editorial;
        this.esGratis = esGratis;
        this.portada = portada;
        this.precio = precio;
    }

    public Libro(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEditorial() {
        return editorial;
    }


    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public boolean isEsGratis() {
        return esGratis;
    }

    public void setEsGratis(boolean esGratis) {
        this.esGratis = esGratis;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(ISBN, libro.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ISBN);
    }
}
