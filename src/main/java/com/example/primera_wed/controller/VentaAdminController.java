package com.example.primera_wed.controller;

import com.example.primera_wed.model.Usuario;
import com.example.primera_wed.service.VentaService;
import com.example.primera_wed.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendedor")
public class VentaAdminController {

    @Autowired private VentaService ventaService;
    @Autowired private ProductoService productoService;

    @GetMapping("/ventas")
    public String moduloVentas(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        if (buscar != null && !buscar.trim().isEmpty()) {
            model.addAttribute("clientes", ventaService.buscarClientes(buscar));
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