package com.example.primera_wed.service;

import com.example.primera_wed.model.*;
import com.example.primera_wed.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VentaService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * Busca clientes activos con el rol 'Cliente'.
     * Ahora utiliza el método avanzado que busca por Nickname, Nombres o Apellidos.
     */
    public List<Usuario> buscarClientes(String criterio) {
        return usuarioRepository.buscarClientesPorCriterio(criterio, "Cliente");
    }

    /**
     * Recupera todas las compras realizadas por un cliente específico.
     */
    public List<Pedido> obtenerHistorialCliente(Long clienteId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return pedidoRepository.findByCliente(cliente);
    }

    /**
     * Registra un cliente nuevo con sus datos en MAYÚSCULAS y
     * procesa su primera compra de forma inmediata.
     */
    @Transactional
    public void registrarClienteYVenta(Usuario nuevoCliente, Long productoId) {
        // La lógica de conversión a MAYÚSCULAS se ejecuta automáticamente
        // gracias a los setters que definimos en la entidad Usuario.
        nuevoCliente.setRol("Cliente");
        nuevoCliente.setActivo(true);

        // Asignamos una contraseña genérica inicial
        nuevoCliente.setPassword(passwordEncoder.encode("123456"));

        usuarioRepository.save(nuevoCliente);

        // Procedemos a registrar la venta vinculada
        realizarVenta(nuevoCliente.getId(), productoId);
    }

    /**
     * Registra una venta para un cliente existente y descuenta el stock.
     */
    @Transactional
    public void realizarVenta(Long clienteId, Long productoId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Validación crítica de inventario
        if (producto.getStock() == null || producto.getStock() <= 0) {
            throw new RuntimeException("Sin stock suficiente para " + producto.getNombre());
        }

        // 1. Crear y guardar el registro del Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProducto(producto);
        pedidoRepository.save(pedido);

        // 2. Descontar stock físicamente en la base de datos
        producto.setStock(producto.getStock() - 1);
        productoRepository.save(producto);
    }
}