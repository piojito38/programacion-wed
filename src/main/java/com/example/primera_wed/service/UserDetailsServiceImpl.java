package com.example.primera_wed.service;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos a tu usuario en la base de datos
        Usuario usuario = usuarioRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Verificamos que su cuenta no esté suspendida
        if (!usuario.isActivo()) {
            throw new UsernameNotFoundException("La cuenta de usuario está inactiva.");
        }

        // 3. Spring Security exige que los roles empiecen con "ROLE_" y estén en mayúsculas
        // Aquí lo adaptamos automáticamente para que no tengas que cambiar los datos que ya tienes.
        String rolFormateado = usuario.getRol().toUpperCase();
        if (!rolFormateado.startsWith("ROLE_")) {
            rolFormateado = "ROLE_" + rolFormateado;
        }

        // 4. Se lo entregamos traducido a Spring Security
        return User.builder()
                .username(usuario.getNickname())
                .password(usuario.getPassword())
                .authorities(rolFormateado)
                .build();
    }
}