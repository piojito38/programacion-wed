package com.example.primera_wed.controller;

import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService; // Importación necesaria
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private ProductoService productoService;

    // Inyectamos el servicio de categorías para que el Index sea dinámico
    @Autowired
    private CategoriaService categoriaService;

    /**
     * Muestra la página principal.
     * Envía productos activos y la lista de categorías para generar las secciones.
     */
    @GetMapping({"/", "/index"})
    public String mostrarIndex(Model model) {
        // Obtenemos solo los productos marcados como activos (borrado lógico)
        model.addAttribute("productos", productoService.obtenerActivos());

        // Enviamos las categorías para que el HTML cree los títulos de sección
        model.addAttribute("categorias", categoriaService.obtenerTodas());

        return "index";
    }

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

    /**
     * Muestra la página de métricas.
     * Envía la lista completa incluyendo productos ocultos para estadísticas.
     */
    @GetMapping("/metricas")
    public String mostrarMetricas(Model model) {
        model.addAttribute("totalProductos", productoService.obtenerTodosParaMetricas());
        return "metricas";
    }
}