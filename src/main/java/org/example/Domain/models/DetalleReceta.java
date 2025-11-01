package org.example.Domain.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detalle_receta")
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK interno para JPA

    @Column(name = "id_medicamento", length = 50)
    private String idMedicamento; // mantiene String original

    private int cantidad;
    @Column(length = 1000)
    private String indicaciones;
    @Column(name = "duracion_dias")
    private int duracionDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", referencedColumnName = "id")
    private Receta receta;

    public DetalleReceta() {}

    public DetalleReceta(String idMedicamento, int cantidad, String indicaciones, int duracionDias) {
        this.idMedicamento = idMedicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getDuracionDias() { return duracionDias; }
    public void setDuracionDias(int duracionDias) { this.duracionDias = duracionDias; }

    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }

    @Override
    public String toString() {
        return "DetalleReceta{" +
                "id=" + id +
                ", idMedicamento='" + idMedicamento + '\'' +
                ", cantidad=" + cantidad +
                ", indicaciones='" + indicaciones + '\'' +
                ", duracionDias=" + duracionDias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetalleReceta that = (DetalleReceta) o;
        return cantidad == that.cantidad &&
                duracionDias == that.duracionDias &&
                Objects.equals(idMedicamento, that.idMedicamento) &&
                Objects.equals(indicaciones, that.indicaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMedicamento, cantidad, indicaciones, duracionDias);
    }
}
