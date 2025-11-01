package org.example.Domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @Column(length = 50)
    private String codigo; // PK, tal como en tu proyecto original

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(length = 100)
    private String presentacion;

    public Medicamento() {}

    public Medicamento(String codigo, String descripcion, String presentacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
    }

    // Getters / Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    @Override
    public String toString() {
        return "Medicamento{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", presentacion='" + presentacion + '\'' +
                '}';
    }
}
