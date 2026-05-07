package com.example.primera_wed.service;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // MÉTODO NUEVO: Vital para el Login
    public Usuario buscarPorNickname(String nickname) {
        return usuarioRepository.findByNickname(nickname).orElse(null);
    }

    // MÉTODO NUEVO: Vital para el DataInit
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public List<Usuario> obtenerActivos() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }

    public void guardar(Usuario usuario) {
        if (usuario.getId() != null) {
            // MODO EDICIÓN
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                // Si la contraseña viene vacía, mantenemos la encriptada que ya existía
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    usuario.setPassword(usuarioExistente.getPassword());
                } else {
                    // Si el admin escribió una nueva, la encriptamos
                    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
            }
        } else {
            // MODO REGISTRO NUEVO
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioRepository.save(usuario);
    }

    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        }
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}