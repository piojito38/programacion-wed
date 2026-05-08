package com.example.primera_wed.repository;

import com.example.primera_wed.model.Queja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuejaRepository extends JpaRepository<Queja, Long> {
    List<Queja> findByEstadoOrderByFechaEnvioDesc(String estado);
}