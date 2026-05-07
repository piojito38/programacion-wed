package com.example.primera_wed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String imagenUrl;

    // NUEVO CAMPO:
    private String categoria;

    public Producto() {
    }

    // Actualizamos el constructor
    public Producto(String nombre, Double precio, String imagenUrl, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    // Nuevos Getters y Setters para Categoría
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    // Dentro de Producto.java
    private boolean activo = true; // Por defecto true

// Actualiza tus constructores para incluirlo si lo deseas,
// o simplemente inicialízalo en la declaración.

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}