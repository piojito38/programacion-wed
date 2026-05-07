package com.example.primera_wed.model;

import jakarta.persistence.*;
import java.util.List; // Necesario para múltiples categorías

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String imagenUrl;

    // NUEVOS CAMPOS PARA STOCK FÍSICO Y DESCUENTOS
    private Integer stock;
    private Double descuento = 0.0;
    private boolean activo = true;

    // CAMBIO CLAVE: Relación para tener más de una categoría
    @ManyToMany
    @JoinTable(
            name = "producto_categorias",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    public Producto() {
    }

    // Constructor actualizado para el inventario retro
    public Producto(String nombre, Double precio, String imagenUrl, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
    }

    // Lógica para calcular el precio con el descuento aplicado
    public Double getPrecioFinal() {
        if (this.descuento > 0) {
            return this.precio - (this.precio * (this.descuento / 100));
        }
        return this.precio;
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

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
}