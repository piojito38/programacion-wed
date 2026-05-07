package com.example.primera_wed.repository;

import com.example.primera_wed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí podrías agregar métodos como findByCorreo si luego implementamos el login real
}