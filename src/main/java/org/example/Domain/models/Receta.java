package org.example.Domain.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recetas")
public class Receta {

    @Id
    @Column(name = "id", length = 50)
    private String id; // manteniendo String como dijiste

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaItem> items = new ArrayList<>();

    @Column(name = "fechaConfeccion")
    private LocalDateTime fechaConfeccion;

    @Column(name = "fechaRetiro")
    private LocalDateTime fechaRetiro;

    @Column(name = "medicoId", length = 255)
    private String medicoId;

    @Column(name = "pacienteId", length = 255)
    private String pacienteId;

    public Receta() {}

    // getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<RecetaItem> getItems() { return items; }
    public void setItems(List<RecetaItem> items) { this.items = items; }
    public LocalDateTime getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(LocalDateTime fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }
    public LocalDateTime getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(LocalDateTime fechaRetiro) { this.fechaRetiro = fechaRetiro; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    // helper para agregar items manteniendo referencia
    public void addItem(RecetaItem item) {
        item.setReceta(this);
        items.add(item);
    }
}
