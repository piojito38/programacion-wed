package com.example.primera_wed.repository;

import com.example.primera_wed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List; // Importante para la búsqueda de múltiples clientes

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    // Busca por el nombre de usuario exacto para el Login
    Optional<Usuario> findByNickname(String nickname);

    // Busca por correo para validar duplicados
    Optional<Usuario> findByCorreo(String correo);

    // NUEVO MÉTODO: Permite al vendedor buscar clientes por nickname de forma flexible
    List<Usuario> findByNicknameContainingIgnoreCaseAndRol(String nickname, String rol);
}