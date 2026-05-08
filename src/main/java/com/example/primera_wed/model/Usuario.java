package com.example.primera_wed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String correo;

    private String nombres;
    private String apellidos;
    private String rol; // Administrador, Vendedor, Cliente
    private String password;
    private boolean activo = true;

    // Constructor vacío obligatorio para JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nickname, String correo, String rol, String password, String nombres, String apellidos) {
        this.nickname = nickname;
        this.correo = correo;
        this.rol = rol;
        this.password = password;
        setNombres(nombres);
        setApellidos(apellidos);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombres() { return nombres; }
    // Fuerza mayúsculas automáticamente
    public void setNombres(String nombres) {
        this.nombres = (nombres != null) ? nombres.toUpperCase() : null;
    }

    public String getApellidos() { return apellidos; }
    // Fuerza mayúsculas automáticamente
    public void setApellidos(String apellidos) {
        this.apellidos = (apellidos != null) ? apellidos.toUpperCase() : null;
    }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}