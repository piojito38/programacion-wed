package com.example.primera_wed.repository;

import com.example.primera_wed.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // <-- No olvides importar List

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Este es el
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

}