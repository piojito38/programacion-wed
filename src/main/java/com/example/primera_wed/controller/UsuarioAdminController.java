package com.example.primera_wed.controller;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        // Obtenemos la lista actualizada de usuarios activos
        model.addAttribute("usuarios", usuarioService.obtenerActivos());
        // Preparamos un objeto vacío para el formulario de creación
        model.addAttribute("nuevoUsuario", new Usuario());
        return "crud-usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoUsuario") Usuario usuario) {
        /* La lógica de conversión a MAYÚSCULAS ya ocurre dentro de la entidad Usuario,
           y la encriptación de contraseña se gestiona en el UsuarioService.
           Simplemente enviamos el objeto completo.
        */
        usuarioService.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuarioExistente = usuarioService.obtenerPorId(id);

        if (usuarioExistente == null) {
            return "redirect:/admin/usuarios?error=no_encontrado";
        }

        // Cargamos el usuario encontrado en el formulario
        model.addAttribute("nuevoUsuario", usuarioExistente);
        // Mantenemos la tabla visible con todos los usuarios
        model.addAttribute("usuarios", usuarioService.obtenerActivos());

        return "crud-usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        // En lugar de borrar físicamente, cambiamos el estado a 'activo = false'
        usuarioService.desactivar(id);
        return "redirect:/admin/usuarios";
    }
}