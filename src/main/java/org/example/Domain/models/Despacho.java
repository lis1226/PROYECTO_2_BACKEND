package org.example.Domain.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "despachos")
public class Despacho {

    @Id
    @Column(name = "id", length = 50)
    private String id; // usando String según mencionaste

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaDespacho")
    private LocalDateTime fechaDespacho;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "farmaceuticoId", length = 255)
    private String farmaceuticoId;

    @Column(name = "recetaId", length = 255)
    private String recetaId;

    public Despacho() {}

    // getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaDespacho() { return fechaDespacho; }
    public void setFechaDespacho(LocalDateTime fechaDespacho) { this.fechaDespacho = fechaDespacho; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getFarmaceuticoId() { return farmaceuticoId; }
    public void setFarmaceuticoId(String farmaceuticoId) { this.farmaceuticoId = farmaceuticoId; }
    public String getRecetaId() { return recetaId; }
    public void setRecetaId(String recetaId) { this.recetaId = recetaId; }
}
