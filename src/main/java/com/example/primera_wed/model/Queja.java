package com.example.primera_wed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quejas")
public class Queja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String correo;
    private String asunto;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaEnvio = LocalDateTime.now();
    private String estado = "PENDIENTE"; // PENDIENTE, EN_PROCESO, RESUELTA

    public Queja() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}