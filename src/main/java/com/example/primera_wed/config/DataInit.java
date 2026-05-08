package com.example.primera_wed.config; // Asegúrate de que el package sea correcto

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Usaremos el nickname "MauAdmin" que es el que vimos que el repo sí reconoce en azul
        String nicknameAdmin = "Admin";
        String passEncriptada = passwordEncoder.encode("admin123");

        usuarioRepository.findByNickname(nicknameAdmin).ifPresentOrElse(
                usuario -> {
                    // Si ya existe MauAdmin, nos aseguramos que su clave sea la encriptada
                    usuario.setPassword(passEncriptada);
                    usuarioRepository.save(usuario);
                    System.out.println("⚡ Usuario admin verificado y actualizado.");
                },
                () -> {
                    // Si no existe, lo creamos
                    Usuario admin = new Usuario();
                    admin.setNickname(nicknameAdmin);
                    admin.setCorreo("admin@playnow.com");
                    admin.setRol("ADMINISTRADOR");
                    admin.setPassword(passEncriptada);
                    admin.setActivo(true);
                    usuarioRepository.save(admin);
                    System.out.println("✅ Usuario admin creado con éxito.");
                }
        );
    }
}