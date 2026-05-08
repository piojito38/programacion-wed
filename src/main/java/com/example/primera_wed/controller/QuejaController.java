package com.example.primera_wed.controller;

import com.example.primera_wed.model.Queja;
import com.example.primera_wed.repository.QuejaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuejaController {

    @Autowired
    private QuejaRepository quejaRepository;

    // Acción para que el cliente envíe su queja
    @PostMapping("/contacto/enviar")
    public String enviarQueja(@ModelAttribute Queja queja) {
        quejaRepository.save(queja);
        return "redirect:/contacto?exito";
    }

    // Vista para Admin y Vendedor (Buzón de Quejas)
    @GetMapping("/admin/quejas")
    public String listarQuejas(Model model) {
        model.addAttribute("quejas", quejaRepository.findAll());
        return "buzon-quejas";
    }
}