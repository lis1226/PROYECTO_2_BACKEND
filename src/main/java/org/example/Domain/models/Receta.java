package org.example.Domain.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "recetas")
public class Receta {

    @Id
    @Column(length = 50)
    private String id; // UUID string - mantenemos tipo String por compatibilidad

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_confeccion")
    private Date fechaConfeccion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_retiro")
    private Date fechaRetiro;

    @Column(length = 30)
    private String estado; // mantenemos String para máxima compatibilidad

    @Column(name = "id_paciente", length = 50)
    private String idPaciente; // mantengo String para no romper el resto del proyecto

    @Column(name = "id_medico", length = 50)
    private String idMedico; // mantengo String

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public Receta() {}

    @PrePersist
    protected void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        if (fechaConfeccion == null) fechaConfeccion = new Date();
        if (estado == null) estado = "confeccionada";
    }

    // Getters / Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(Date fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }

    public Date getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(Date fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public List<DetalleReceta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleReceta> detalles) {
        this.detalles.clear();
        if (detalles != null) {
            detalles.forEach(this::addDetalle);
        }
    }

    public void addDetalle(DetalleReceta detalle) {
        detalle.setReceta(this);
        this.detalles.add(detalle);
    }

    public boolean modificarDetalle(DetalleReceta detalleModificado) {
        for (int i = 0; i < detalles.size(); i++) {
            if (Objects.equals(detalles.get(i).getIdMedicamento(), detalleModificado.getIdMedicamento())) {
                detalleModificado.setId(detalles.get(i).getId()); // preserve PK
                detalleModificado.setReceta(this);
                detalles.set(i, detalleModificado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarDetallePorMedicamento(String idMedicamento) {
        return detalles.removeIf(d -> Objects.equals(d.getIdMedicamento(), idMedicamento));
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id='" + id + '\'' +
                ", fechaConfeccion=" + fechaConfeccion +
                ", fechaRetiro=" + fechaRetiro +
                ", estado='" + estado + '\'' +
                ", idPaciente='" + idPaciente + '\'' +
                ", idMedico='" + idMedico + '\'' +
                ", detalles=" + detalles +
                '}';
    }
}
