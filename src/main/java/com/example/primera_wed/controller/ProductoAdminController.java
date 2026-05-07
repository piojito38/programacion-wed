package com.example.primera_wed.controller;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService; // Importante añadir el import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class ProductoAdminController {

    @Autowired
    private ProductoService productoService;

    // 1. CORRECCIÓN: Inyectar el servicio de categorías
    @Autowired
    private CategoriaService categoriaService;

    // 2. CORRECCIÓN: Un solo método listar que incluya todo
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.obtenerActivos());
        model.addAttribute("nuevoProducto", new Producto());
        // Añadimos las categorías para que el selector del HTML funcione
        model.addAttribute("categoriasDisponibles", categoriaService.obtenerTodas());
        return "crud-productos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoProducto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("nuevoProducto", productoService.obtenerPorId(id));
        model.addAttribute("productos", productoService.obtenerActivos());
        // También necesitamos las categorías aquí por si Mau decide editar la categoría del juego
        model.addAttribute("categoriasDisponibles", categoriaService.obtenerTodas());
        return "crud-productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.desactivar(id);
        return "redirect:/admin/productos";
    }
}