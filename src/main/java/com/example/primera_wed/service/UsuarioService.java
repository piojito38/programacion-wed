package com.example.primera_wed.service;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerActivos() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }

    public void guardar(Usuario usuario) {
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

    @PostConstruct
    public void usuariosIniciales() {
        if (usuarioRepository.count() == 0) {
            // Te agregamos como el Admin principal del sistema
            usuarioRepository.save(new Usuario("Mau", "mau@playnow.com", "Administrador", "admin123"));
            usuarioRepository.save(new Usuario("Aylin", "aylin@playnow.com", "Vendedor", "vendedor123"));
            usuarioRepository.save(new Usuario("Pepe", "pepe@gmail.com", "Cliente", "cliente123"));
        }
    }
}