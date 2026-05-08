package com.example.primera_wed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Muchos pedidos pueden pertenecer a un mismo cliente
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne // Muchos pedidos pueden ser del mismo producto
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private LocalDateTime fechaVenta = LocalDateTime.now();

    // Estado para gestionar si el cliente ya recogió su juego físico
    private String estadoRecojo = "PENDIENTE";

    public Pedido() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    public String getEstadoRecojo() { return estadoRecojo; }
    public void setEstadoRecojo(String estadoRecojo) { this.estadoRecojo = estadoRecojo; }
}