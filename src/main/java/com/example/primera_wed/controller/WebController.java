package com.example.primera_wed.controller;

import com.example.primera_wed.model.Producto; // Import necesario para la lista
import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; // ESTE ES EL IMPORT QUE SUELE FALTAR

import java.util.List; // IMPORT NECESARIO PARA MANEJAR LA LISTA DE RESULTADOS

@Controller
public class WebController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping({"/", "/index"})
    public String mostrarIndex(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        List<Producto> productos;

        // Si hay un término de búsqueda, usamos el nuevo método del servicio
        if (buscar != null && !buscar.trim().isEmpty()) {
            productos = productoService.buscarPorNombre(buscar);
        } else {
            // Si no hay búsqueda, mostramos todos los activos como antes
            productos = productoService.obtenerActivos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.obtenerTodas());

        return "index";
    }

    // El resto de tus métodos (contacto, publicidad, etc.) se mantienen igual...
    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }

    @GetMapping("/publicidad")
    public String mostrarPublicidad() {
        return "publicidad";
    }

    @GetMapping("/perfil")
    public String mostrarPerfil() {
        return "perfil";
    }

    @GetMapping("/compra")
    public String mostrarCompra() {
        return "compra";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/gestion")
    public String mostrarGestion() {
        return "gestion";
    }

    @GetMapping("/metricas")
    public String mostrarMetricas(Model model) {
        model.addAttribute("totalProductos", productoService.obtenerTodosParaMetricas());
        return "metricas";
    }
}