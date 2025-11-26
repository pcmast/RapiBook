package com.actividades.rapibookgit.model;

public class Libro {
    private int id;
    private String titulo;
    private String ano;
    private Editorial editorial;

    public Libro(int id, String titulo, String ano, Editorial editorial) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.editorial = editorial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}
