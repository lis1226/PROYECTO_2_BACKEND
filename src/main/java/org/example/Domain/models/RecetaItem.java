package org.example.Domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receta_items")
public class RecetaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_codigo", referencedColumnName = "codigo")
    private Medicamento medicamento; // asume que ya existe class Medicamento con @Id codigo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", referencedColumnName = "id")
    private Receta receta;

    public RecetaItem() {}

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }
}
