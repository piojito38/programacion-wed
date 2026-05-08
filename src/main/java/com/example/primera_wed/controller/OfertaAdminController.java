package com.example.primera_wed.controller;

import com.example.primera_wed.model.OfertaDestacada;
import com.example.primera_wed.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/ofertas")
public class OfertaAdminController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ofertas", ofertaService.obtenerTodas());
        model.addAttribute("nuevaOferta", new OfertaDestacada());
        return "crud-oferta";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("nuevaOferta") OfertaDestacada oferta) {
        ofertaService.guardar(oferta);
        return "redirect:/admin/ofertas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ofertaService.eliminar(id);
        return "redirect:/admin/ofertas";
    }
}