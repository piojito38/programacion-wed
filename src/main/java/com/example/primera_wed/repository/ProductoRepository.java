package com.example.primera_wed.repository;

import com.example.primera_wed.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository ya incluye métodos como findAll(), save(), deleteById(), etc.
}