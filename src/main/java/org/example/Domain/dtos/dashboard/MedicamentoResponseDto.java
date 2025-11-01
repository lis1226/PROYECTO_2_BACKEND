package org.example.Domain.dtos.dashboard;


public class MedicamentoResponseDto {
    private String codigo;
    private String descripcion;
    private String presentacion;

    // Constructor vacío requerido por Gson y Hibernate
    public MedicamentoResponseDto() {
    }

    // Constructor usado en DashboardController
    public MedicamentoResponseDto(String codigo, String descripcion, String presentacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
}
