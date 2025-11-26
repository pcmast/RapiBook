package com.actividades.rapibookgit.model;

public class LibroAutor {
    private Autor autor;
    private Libro libro;

    public LibroAutor(Autor autor, Libro libro) {
        this.autor = autor;
        this.libro = libro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
