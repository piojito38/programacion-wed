package com.example.primera_wed.controller;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.model.Pedido;
import com.example.primera_wed.service.VentaService;
import com.example.primera_wed.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vendedor")
public class VentaAdminController {

    @Autowired private VentaService ventaService;
    @Autowired private ProductoService productoService;

    @GetMapping("/ventas")
    public String moduloVentas(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        if (buscar != null && !buscar.trim().isEmpty()) {
            List<Usuario> clientes = ventaService.buscarClientes(buscar);
            model.addAttribute("clientes", clientes);

            // NUEVO: Creamos un "mapa" para enviar el historial de cada cliente encontrado
            Map<Long, List<Pedido>> historialClientes = new HashMap<>();
            for (Usuario c : clientes) {
                historialClientes.put(c.getId(), ventaService.obtenerHistorialCliente(c.getId()));
            }
            model.addAttribute("historialClientes", historialClientes);
        }
        model.addAttribute("productos", productoService.obtenerActivos());
        return "crud-ventas";
    }

    @PostMapping("/registrar-cliente-venta")
    public String registrarYVender(@ModelAttribute Usuario nuevoCliente, @RequestParam Long productoId) {
        ventaService.registrarClienteYVenta(nuevoCliente, productoId);
        return "redirect:/vendedor/ventas?exito";
    }

    @PostMapping("/agregar-venta")
    public String agregarVenta(@RequestParam Long clienteId, @RequestParam Long productoId) {
        try {
            ventaService.realizarVenta(clienteId, productoId);
            return "redirect:/vendedor/ventas?exito";
        } catch (Exception e) {
            return "redirect:/vendedor/ventas?error=" + e.getMessage();
        }
    }
}