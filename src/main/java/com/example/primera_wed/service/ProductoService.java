package com.example.primera_wed.service;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Devuelve solo los productos que están marcados como activos.
     * Úsalo para la tienda (index) y la tabla de gestión actual.
     */
    public List<Producto> obtenerActivos() {
        return productoRepository.findAll().stream()
                .filter(Producto::isActivo)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve todos los registros sin filtrar.
     * Úsalo para las métricas para contar incluso los juegos "ocultos".
     */
    public List<Producto> obtenerTodosParaMetricas() {
        return productoRepository.findAll();
    }

    @PostConstruct
    public void cargarDatosDePrueba() {
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto("Valorant", 0.0, "/img/valorant.jpg", "Acción"));
            productoRepository.save(new Producto("Doom Eternal", 39.99, "/img/doom.avif", "Acción"));
            productoRepository.save(new Producto("Outlast 2", 19.99, "/img/outlast.webp", "Terror"));
            productoRepository.save(new Producto("Aventura Épica", 59.99, "/img/zelda.avif", "Aventura"));

            System.out.println("¡Datos de prueba cargados en la base de datos H2!");
        }
    }

    public void guardar(Producto producto) {
        // Al guardar, nos aseguramos de que el estado activo se mantenga
        // (por defecto es true en el modelo)
        productoRepository.save(producto);
    }

    /**
     * Borrado Lógico: En lugar de usar deleteById, buscamos el producto
     * y cambiamos su estado a falso.
     */
    public void desactivar(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setActivo(false); // Lo marcamos como inactivo/oculto
            productoRepository.save(producto);
        }
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Si alguna vez necesitas borrar uno de verdad de la base de datos:
    public void eliminarPermanente(Long id) {
        productoRepository.deleteById(id);
    }

    // Dentro de ProductoService.java
    public List<Producto> buscarPorNombre(String termino) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(termino);
    }
}