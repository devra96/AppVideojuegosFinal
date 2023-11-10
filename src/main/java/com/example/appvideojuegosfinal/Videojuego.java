package com.example.appvideojuegosfinal;

public class Videojuego {
    private String nombre, categoria, plataforma;

    public Videojuego(String nombre, String categoria, String plataforma) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.plataforma = plataforma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", plataforma='" + plataforma + '\'' +
                '}';
    }
}
