package com.example.primera_wed.controller;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class ProductoAdminController {

    @Autowired private ProductoService productoService;
    @Autowired private CategoriaService categoriaService;

    // CORRECCIÓN 1: Integrar el buscador moderno
    @GetMapping
    public String listar(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        // Si Mau escribe algo en el buscador, filtramos. Si no, mostramos todos.
        if (buscar != null && !buscar.trim().isEmpty()) {
            model.addAttribute("productos", productoService.buscarPorNombre(buscar));
        } else {
            model.addAttribute("productos", productoService.obtenerActivos());
        }

        model.addAttribute("nuevoProducto", new Producto());
        model.addAttribute("categoriasDisponibles", categoriaService.obtenerTodas());
        return "crud-productos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevoProducto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    // CORRECCIÓN 2: Blindar la edición contra errores Null
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto p = productoService.obtenerPorId(id);

        // Si el juego no existe en la base de datos, redirigimos al inicio para evitar que la página explote
        if (p == null) {
            return "redirect:/admin/productos";
        }

        model.addAttribute("nuevoProducto", p);
        model.addAttribute("productos", productoService.obtenerActivos());
        model.addAttribute("categoriasDisponibles", categoriaService.obtenerTodas());
        return "crud-productos";
    }

    @PostMapping("/modificar-stock")
    public String modStock(@RequestParam("id") Long id, @RequestParam("cantidad") int cantidad) {
        try {
            productoService.modificarStock(id, cantidad);
        } catch (Exception e) {
            // Si intentan bajar el stock a menos de 0, simplemente recargamos la página sin guardar
        }
        return "redirect:/admin/productos";
    }
}