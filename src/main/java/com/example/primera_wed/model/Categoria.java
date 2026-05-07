package com.example.primera_wed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Evita categorías duplicadas
    private String nombre;

    @Column(length = 500) // Permite descripciones más extensas
    private String descripcion;

    private Integer prioridad;

    // Constructor vacío (Obligatorio para JPA)
    public Categoria() {}

    // Constructor con parámetros (Usado en tu CategoriaService)
    public Categoria(String nombre, String descripcion, Integer prioridad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
}