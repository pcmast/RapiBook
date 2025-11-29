package com.actividades.rapibookgit.model;

import com.actividades.rapibookgit.DAO.LibroDAO;

import java.util.List;

public class Prestamo {

    private int id;
    private Usuario usuario;
    private Libro libro;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(int id, Usuario usuario, Libro libro, String fechaPrestamo, String fechaDevolucion) {
        this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }
    public Prestamo(Usuario usuario, Libro libro, String fechaPrestamo, String fechaDevolucion) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void recogerLibro(){
        List<Libro> libros = LibroDAO.todosLosLibros();
        for (Libro libro1 : libros){
            if (libro1.getISBN().equals(libro.getISBN())){
                libro.setTitulo(libro1.getTitulo());
            }
        }
    }
    @Override
    public String toString() {
        recogerLibro();
        return "libro = " + libro.getTitulo() +
                ", fechaPrestamo= " + fechaPrestamo + '\'' +
                ", fechaDevolucion= " + fechaDevolucion;
    }
}
