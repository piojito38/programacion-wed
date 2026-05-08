package com.example.primera_wed.service;

import com.example.primera_wed.model.OfertaDestacada;
import com.example.primera_wed.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public List<OfertaDestacada> obtenerTodas() {
        return ofertaRepository.findAll();
    }

    public void guardar(OfertaDestacada oferta) {
        if (oferta.isActiva()) {
            // Si la nueva oferta se marca como activa, desactivamos el resto para que solo haya una
            List<OfertaDestacada> todas = ofertaRepository.findAll();
            for (OfertaDestacada o : todas) {
                o.setActiva(false);
                ofertaRepository.save(o);
            }
        }
        ofertaRepository.save(oferta);
    }

    public OfertaDestacada obtenerActiva() {
        return ofertaRepository.findByActivaTrue().orElse(null);
    }

    public void eliminar(Long id) {
        ofertaRepository.deleteById(id);
    }
}