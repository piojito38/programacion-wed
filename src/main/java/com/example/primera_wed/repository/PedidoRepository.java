package com.example.primera_wed.repository;

import com.example.primera_wed.model.Pedido;
import com.example.primera_wed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Método para ver el historial de compras de un cliente específico
    List<Pedido> findByCliente(Usuario cliente);
}