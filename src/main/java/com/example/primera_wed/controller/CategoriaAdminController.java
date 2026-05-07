package com.example.primera_wed.controller;

import com.example.primera_wed.model.Categoria;
import com.example.primera_wed.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias") // Ruta base para categorías
public class CategoriaAdminController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        model.addAttribute("nuevaCategoria", new Categoria());
        return "crud-categorias";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaCategoria") Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("nuevaCategoria", categoriaService.obtenerPorId(id));
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        return "crud-categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "redirect:/admin/categorias";
    }
}