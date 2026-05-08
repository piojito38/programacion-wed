package com.example.primera_wed.repository;

import com.example.primera_wed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca por el nombre de usuario exacto para el Login
    Optional<Usuario> findByNickname(String nickname);

    // Busca por correo para validar duplicados
    Optional<Usuario> findByCorreo(String correo);

    /**
     * BÚSQUEDA AVANZADA PARA VENTAS
     * Busca coincidencias en nombres, apellidos o nickname, filtrando solo por rol 'Cliente'.
     * El uso de UPPER en la consulta asegura que coincida con tus datos en mayúsculas.
     */
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol AND (" +
            "UPPER(u.nickname) LIKE UPPER(CONCAT('%', :termino, '%')) OR " +
            "UPPER(u.nombres) LIKE UPPER(CONCAT('%', :termino, '%')) OR " +
            "UPPER(u.apellidos) LIKE UPPER(CONCAT('%', :termino, '%')))")
    List<Usuario> buscarClientesPorCriterio(@Param("termino") String termino, @Param("rol") String rol);

    // Mantenemos este por si tu Service aún lo referencia, para evitar errores de compilación inmediatos
    List<Usuario> findByNicknameContainingIgnoreCaseAndRol(String nickname, String rol);
}