package com.example.primera_wed.service;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerActivos() {
        return productoRepository.findAll().stream()
                .filter(Producto::isActivo)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS RECUPERADOS PARA EL WEBCONTROLLER ---
    public List<Producto> buscarPorNombre(String termino) {
        if (termino != null && !termino.trim().isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(termino);
        }
        return obtenerActivos();
    }

    public List<Producto> obtenerTodosParaMetricas() {
        return productoRepository.findAll();
    }
    // --------------------------------------------------

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void guardar(Producto producto) {
        if (producto.getId() != null) {
            Producto existente = productoRepository.findById(producto.getId()).orElse(null);
            if (existente != null) {
                existente.setDescuento(producto.getDescuento());
                // NUEVO: Ahora Java también guarda el cálculo final del stock que enviaremos
                existente.setStock(producto.getStock());
                productoRepository.save(existente);
            }
        } else {
            productoRepository.save(producto);
        }
    }

    @Transactional
    public void modificarStock(Long id, int cantidad) {
        Producto p = productoRepository.findById(id).orElse(null);
        if (p != null) {
            int nuevoStock = p.getStock() + cantidad;
            if (nuevoStock < 0) throw new IllegalArgumentException("Stock insuficiente");
            p.setStock(nuevoStock);
            productoRepository.save(p);
        }
    }

    @Transactional
    public void desactivar(Long id) {
        Producto p = obtenerPorId(id);
        if (p != null) {
            p.setActivo(false);
            productoRepository.save(p);
        }
    }
}