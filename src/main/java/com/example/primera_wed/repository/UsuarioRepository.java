package com.example.primera_wed.repository;

import com.example.primera_wed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Si tu variable en el modelo es 'nickname', este nombre es correcto
    Optional<Usuario> findByNickname(String nickname);

    // Si tu variable en el modelo es 'correo', este nombre es correcto
    Optional<Usuario> findByCorreo(String correo);
}