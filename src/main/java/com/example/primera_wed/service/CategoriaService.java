package com.example.primera_wed.service;

import com.example.primera_wed.model.Categoria;
import com.example.primera_wed.repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @PostConstruct
    public void datosIniciales() {
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria("Acción", "Juegos de combate y disparos", 1));
            categoriaRepository.save(new Categoria("Terror", "Horror y supervivencia", 2));
            categoriaRepository.save(new Categoria("Aventura", "Exploración y narrativa", 3));
        }
    }
}