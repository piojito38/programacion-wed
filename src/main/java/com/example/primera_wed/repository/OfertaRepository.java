package com.example.primera_wed.repository;

import com.example.primera_wed.model.OfertaDestacada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository<OfertaDestacada, Long> {
    Optional<OfertaDestacada> findByActivaTrue();
}