package com.example.primera_wed.controller;

import com.example.primera_wed.model.Producto;
import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.model.Pedido;
import com.example.primera_wed.model.OfertaDestacada;
import com.example.primera_wed.repository.PedidoRepository;
import com.example.primera_wed.service.ProductoService;
import com.example.primera_wed.service.CategoriaService;
import com.example.primera_wed.service.UsuarioService;
import com.example.primera_wed.service.OfertaService;
import com.example.primera_wed.service.VentaService; // IMPORTANTE: Servicio de ventas
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // IMPORTANTE
import org.springframework.web.bind.annotation.PostMapping; // IMPORTANTE
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
    private PedidoRepository pedidoRepository;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private VentaService ventaService; // Inyectamos el servicio para descontar stock

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
    public String mostrarPublicidad(Model model) {
        // Obtenemos la oferta que está marcada como activa en la base de datos
        OfertaDestacada oferta = ofertaService.obtenerActiva();

        // Si no hay ninguna oferta activa en la BD, creamos un objeto vacío temporal
        if (oferta == null) {
            oferta = new OfertaDestacada();
            oferta.setTitulo("PRÓXIMAMENTE");
            oferta.setSubtitulo("Estamos preparando las mejores ofertas para ti.");
        }

        model.addAttribute("oferta", oferta);
        return "publicidad";
    }

    // ==========================================
    // NUEVOS MÉTODOS PARA EL FLUJO DE COMPRA
    // ==========================================

    // 1. Mostrar la página dinámica de un juego específico
    @GetMapping("/compra/{id}")
    public String mostrarCompra(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);

        if (producto == null) {
            return "redirect:/"; // Si el juego no existe o el ID es inválido, vuelve al inicio
        }

        model.addAttribute("producto", producto);
        return "compra";
    }

    // 2. Procesar la compra (Descontar stock y registrar pedido)
    @PostMapping("/realizar-compra")
    public String realizarCompraWeb(@RequestParam Long productoId, Authentication authentication) {
        // Verificamos que el usuario tenga sesión iniciada
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                Usuario cliente = usuarioService.buscarPorNickname(authentication.getName());

                // Realizamos la venta usando el servicio que ya tiene la lógica de validación y stock
                ventaService.realizarVenta(cliente.getId(), productoId);

                // Si todo sale bien, lo enviamos a su biblioteca
                return "redirect:/perfil?compraExito";
            } catch (Exception e) {
                // Si falta stock o hay un error, lo regresamos a la pantalla del juego con un mensaje
                return "redirect:/compra/" + productoId + "?error=stock";
            }
        }
        return "redirect:/login"; // Por seguridad, si no está logueado, se va al login
    }

    // ==========================================

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