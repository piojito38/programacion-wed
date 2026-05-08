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

    // Buscar clientes (rol Cliente) por nickname
    public List<Usuario> buscarClientes(String nickname) {
        return usuarioRepository.findByNicknameContainingIgnoreCaseAndRol(nickname, "Cliente");
    }

    // Registrar un nuevo cliente y asignarle su primera venta
    @Transactional
    public void registrarClienteYVenta(Usuario nuevoCliente, Long productoId) {
        nuevoCliente.setRol("Cliente");
        nuevoCliente.setActivo(true);
        nuevoCliente.setPassword(passwordEncoder.encode("123456")); // Clave temporal
        usuarioRepository.save(nuevoCliente);
        realizarVenta(nuevoCliente.getId(), productoId);
    }

    // Realizar venta a un cliente existente
    @Transactional
    public void realizarVenta(Long clienteId, Long productoId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() <= 0) {
            throw new RuntimeException("Sin stock suficiente");
        }

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProducto(producto);
        pedidoRepository.save(pedido);

        // Descontar stock automáticamente
        producto.setStock(producto.getStock() - 1);
        productoRepository.save(producto);
    }
}