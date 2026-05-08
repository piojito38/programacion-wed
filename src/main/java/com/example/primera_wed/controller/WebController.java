package com.example.primera_wed.controller;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.model.Pedido; // IMPORTANTE
import com.example.primera_wed.repository.PedidoRepository; // IMPORTANTE
import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService;
import com.example.primera_wed.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoRepository pedidoRepository; // Inyectado correctamente

    @GetMapping({"/", "/index"})
    public String mostrarIndex(@RequestParam(name = "buscar", required = false) String buscar,
                               Model model,
                               Authentication authentication) {
        List<Producto> productos;

        if (buscar != null && !buscar.trim().isEmpty()) {
            productos = productoService.buscarPorNombre(buscar);
        } else {
            productos = productoService.obtenerActivos();
        }

        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = usuarioService.buscarPorNickname(authentication.getName());
            model.addAttribute("usuarioLogueado", usuario);
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.obtenerTodas());

        return "index";
    }

    // UN SOLO MÉTODO PARA PERFIL (Corregido y Unificado)
    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String nickname = authentication.getName();
            Usuario usuario = usuarioService.buscarPorNickname(nickname);

            // Buscamos los pedidos reales vinculados a este usuario
            List<Pedido> misPedidos = pedidoRepository.findByCliente(usuario);

            model.addAttribute("usuario", usuario);
            model.addAttribute("pedidos", misPedidos);
            return "perfil";
        }
        return "redirect:/login";
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }

    @GetMapping("/publicidad")
    public String mostrarPublicidad() {
        return "publicidad";
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