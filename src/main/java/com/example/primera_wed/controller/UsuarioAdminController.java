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
        model.addAttribute("usuarios", usuarioService.obtenerActivos());
        model.addAttribute("nuevoUsuario", new Usuario());
        return "crud-usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoUsuario") Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("nuevoUsuario", usuarioService.obtenerPorId(id));
        model.addAttribute("usuarios", usuarioService.obtenerActivos());
        return "crud-usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return "redirect:/admin/usuarios";
    }
}