package com.example.primera_wed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ofertas_destacadas")
public class OfertaDestacada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String etiqueta;

    @Column(columnDefinition = "TEXT")
    private String subtitulo;

    private String descuento;
    private Double precioOriginal;
    private Double precioOferta;
    private String imagenUrl;
    private String tiempoLimite;

    private String detalle1Titulo;
    private String detalle1Texto;
    private String detalle2Titulo;
    private String detalle2Texto;
    private String detalle3Titulo;
    private String detalle3Texto;

    private boolean activa = false;

    public OfertaDestacada() {}

    // GETTERS Y SETTERS COMPLETOS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public String getDescuento() { return descuento; }
    public void setDescuento(String descuento) { this.descuento = descuento; }

    public Double getPrecioOriginal() { return precioOriginal; }
    public void setPrecioOriginal(Double precioOriginal) { this.precioOriginal = precioOriginal; }

    public Double getPrecioOferta() { return precioOferta; }
    public void setPrecioOferta(Double precioOferta) { this.precioOferta = precioOferta; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getTiempoLimite() { return tiempoLimite; }
    public void setTiempoLimite(String tiempoLimite) { this.tiempoLimite = tiempoLimite; }

    public String getDetalle1Titulo() { return detalle1Titulo; }
    public void setDetalle1Titulo(String detalle1Titulo) { this.detalle1Titulo = detalle1Titulo; }

    public String getDetalle1Texto() { return detalle1Texto; }
    public void setDetalle1Texto(String detalle1Texto) { this.detalle1Texto = detalle1Texto; }

    public String getDetalle2Titulo() { return detalle2Titulo; }
    public void setDetalle2Titulo(String detalle2Titulo) { this.detalle2Titulo = detalle2Titulo; }

    public String getDetalle2Texto() { return detalle2Texto; }
    public void setDetalle2Texto(String detalle2Texto) { this.detalle2Texto = detalle2Texto; }

    public String getDetalle3Titulo() { return detalle3Titulo; }
    public void setDetalle3Titulo(String detalle3Titulo) { this.detalle3Titulo = detalle3Titulo; }

    public String getDetalle3Texto() { return detalle3Texto; }
    public void setDetalle3Texto(String detalle3Texto) { this.detalle3Texto = detalle3Texto; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}